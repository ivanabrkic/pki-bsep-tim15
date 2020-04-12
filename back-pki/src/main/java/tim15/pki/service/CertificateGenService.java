package tim15.pki.service;

import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim15.pki.dto.CertificateGenDTO;
import tim15.pki.dto.TextMessage;
import tim15.pki.dto.X500NameCustom;
import tim15.pki.model.Extension;
import tim15.pki.model.SubjectData;
import tim15.pki.model.ValidityPeriod;
import tim15.pki.model.enums.CertificateStatus;
import tim15.pki.repository.CertificateRepository;
import tim15.pki.repository.ExtensionRepository;
import tim15.pki.repository.SystemEntityRepository;
import tim15.pki.repository.ValidityPeriodRepository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class CertificateGenService {

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private ExtensionRepository extensionRepository;

    @Autowired
    private SystemEntityRepository systemEntityRepository;

    @Autowired
    private ValidityPeriodRepository validityPeriodRepository;

    @Autowired
    private LoggerService loggerService;

    @Autowired
    private CertificateReaderService certificateReaderService;

    public List<tim15.pki.model.Certificate> getAllCAs(){
        List<tim15.pki.model.Certificate> certificates = certificateRepository.findByIsCAAndIsActiveAndCertificateStatus(true, true, CertificateStatus.VALID);
        for(tim15.pki.model.Certificate c : certificates){
            c.setValidityPeriod(validityPeriodRepository.findByCertificate(c));
        }
        return certificates;
    }

    public List<Extension> getAllExtensions(){
        return extensionRepository.findAll();
    }

    public TextMessage generateCertificate(CertificateGenDTO certificateGenDTO) {

        try {
            char [] password = {'b','s','e','p'};
            List<Object> subjectKey =  createSubjectData(certificateGenDTO.getX500NameCustom());

            JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
            builder = builder.setProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

            SubjectData subjectData = (SubjectData) subjectKey.get(0);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            X500Name parentName;
            ContentSigner contentSigner;
            PublicKey issuerPublicKey;
            String issuerCommonName;
            /// Da li je root ili ne
            if (certificateGenDTO.getParentSerialNumber().equals("")){
                parentName = subjectData.getName();
                contentSigner = builder.build((PrivateKey) subjectKey.get(1));
                issuerPublicKey = subjectData.getPublicKey();
                issuerCommonName = certificateGenDTO.getX500NameCustom().getCommonName();
            }
            else{
                parentName = certificateReaderService.readIssuerFromStore("keystoreCA.jks", certificateGenDTO.getParentSerialNumber(), password, password).getName();
                contentSigner = builder.build(certificateReaderService.readPrivateKey("keystoreCA.jks", "bsep", certificateGenDTO.getParentSerialNumber(), "bsep"));
                issuerPublicKey = certificateReaderService.readCertificate("keystoreCA.jks", "bsep", certificateGenDTO.getParentSerialNumber()).getPublicKey();
                issuerCommonName = parentName.getRDNs(BCStyle.CN)[0].getFirst().getValue().toString();
            }

            X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(parentName,
                    new BigInteger(subjectData.getName().getRDNs(BCStyle.SERIALNUMBER)[0].getFirst().getValue().toString()),
                    dateFormat.parse(certificateGenDTO.getStartDate()),
                    dateFormat.parse(certificateGenDTO.getEndDate()),
                    subjectData.getName(),
                    subjectData.getPublicKey());

            // Kako se setuje verzija sertifikata??
            // CertTemplateBuilder certTemplateBuilder = new CertTemplateBuilder(); -- ovde postoji ta opcija

            ///////////// EKESTENZIJE////////////////
            BasicConstraints basicConstraints = new BasicConstraints(certificateGenDTO.getIsCA());
            certGen.addExtension(new ASN1ObjectIdentifier(extensionRepository.findByName("Basic Constraints").getOid()), true, basicConstraints);

            JcaX509ExtensionUtils extensionUtils = new JcaX509ExtensionUtils();
            AuthorityKeyIdentifier authorityKeyIdentifier = extensionUtils
                    .createAuthorityKeyIdentifier(issuerPublicKey);
            certGen.addExtension(new ASN1ObjectIdentifier(extensionRepository.findByName("Authority Key Identifier").getOid()), false, authorityKeyIdentifier);

            SubjectKeyIdentifier subjectKeyIdentifier = extensionUtils
                    .createSubjectKeyIdentifier(subjectData.getPublicKey());
            certGen.addExtension(new ASN1ObjectIdentifier(extensionRepository.findByName("Subject Key Identifier").getOid()), false, subjectKeyIdentifier);

//            InvalidityDateExtension invalidityDateExtension = new InvalidityDateExtension(true, certificateGenDTO.getEndDate()); // EVENTUALNO OVO
            //////////////////////////////////////////

            X509CertificateHolder certHolder = certGen.build(contentSigner);

            JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
            certConverter = certConverter.setProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

            X509Certificate certificate = certConverter.getCertificate(certHolder);

            //////// Priprema liste za slanje funkciji za cuvanje u keystore
            List<Object> certificateKeySerialIsCA = new ArrayList<>();
            certificateKeySerialIsCA.add(certificate);
            certificateKeySerialIsCA.add(subjectKey.get(1));
            certificateKeySerialIsCA.add(parentName.getRDNs(BCStyle.SERIALNUMBER)[0].getFirst().getValue().toString());
            certificateKeySerialIsCA.add(certificateGenDTO.getIsCA());

            loggerService.print("Certificate successfully created.");

            saveKeyStore(certificateKeySerialIsCA, password);

            ValidityPeriod validityPeriod = new ValidityPeriod(dateFormat.parse(certificateGenDTO.getStartDate()), dateFormat.parse(certificateGenDTO.getEndDate()));
            saveDatabase(certificateKeySerialIsCA, certificateGenDTO.getParentSerialNumber(), validityPeriod, certificateGenDTO.getX500NameCustom().getCommonName(), issuerCommonName);

            return new TextMessage("Certificate successfully created and saved.");

        } catch (CertificateEncodingException e) {
            loggerService.print("CertificateEncodingException in function generateCertificate():" + e.getMessage());
        } catch (IllegalArgumentException e) {
            loggerService.print("IllegalArgumentException in function generateCertificate() ; message = " + e.getMessage() + "; cause = " +  e.getCause() + ";stack trace = " + e.getStackTrace());
        } catch (IllegalStateException e) {
            loggerService.print("IllegalStateException in function generateCertificate():" + e.getMessage());
        } catch (OperatorCreationException e) {
            loggerService.print("OperatorCreationException in function generateCertificate():" + e.getMessage());
        } catch (CertificateException e) {
            loggerService.print("CertificateException in function generateCertificate():" + e.getMessage());
        }
        catch (ParseException e) {
            loggerService.print("ParseException in function generateCertificate():" + e.getMessage());
        }
        catch (Exception e) {
            loggerService.print("Exception in function generateCertificate():" + e.getMessage());
        }

        loggerService.print("Certificate failed to create and save.");
        return new TextMessage("Certificate failed to create and save.");
    }

    private void saveDatabase(List<Object> certificateKeySerialIsCA, String parentSerialNumber, ValidityPeriod validityPeriod, String subjectName, String issuerName){
        try {
            tim15.pki.model.Certificate certificate = tim15.pki.model.Certificate.builder().setIsActive(true)
                    .setIsCA((Boolean) certificateKeySerialIsCA.get(3))
                    .setIssuedTo(subjectName)
                    .setIssuedBy(issuerName)
                    .setCertificateStatus(CertificateStatus.VALID)
                    .setSerialNumber((String) certificateKeySerialIsCA.get(2))
                    .setRevokeReason(null).createCertificate();

            loggerService.print(String.valueOf(((String)certificateKeySerialIsCA.get(2)).length()));

            certificate.setCertificateChildren(null);

            if (parentSerialNumber.equals("")) {
                certificate.setCertificateParents(null);
            } else {
                certificate.getCertificateParents().add(certificateRepository.getOneBySerialNumber(parentSerialNumber));
            }
            certificateRepository.save(certificate);

            validityPeriod.setCertificate(certificate);
            validityPeriodRepository.save(validityPeriod);

            loggerService.print("Certificate successfully saved in database.");
        }
        catch (Exception e){
            loggerService.print("Exception in function saveDatabase() :" + e.getMessage());
        }
    }

    private void saveKeyStore(List<Object> certificateKeySerialIsCA, char[] password){

        try {
            // UBACITI KLIJENT, SERVER, USER VRSTE KORISNIKA
            X509Certificate x500Certificate = (X509Certificate) certificateKeySerialIsCA.get(0);
            PrivateKey privateKey = (PrivateKey) certificateKeySerialIsCA.get(1);
            String parentSerialNumber = (String) certificateKeySerialIsCA.get(2);
            boolean isCA = (Boolean) certificateKeySerialIsCA.get(3);

            KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");

            java.security.cert.Certificate[] newChain;

            Path path = Paths.get("./keystoreCA.jks");
            if(Files.exists(path)) {
                keyStore.load(new FileInputStream("keystoreCA.jks"), password);
                // Preuzmi chain roditelja
                if (parentSerialNumber == x500Certificate.getSerialNumber().toString()) {
                    java.security.cert.Certificate[] chain = keyStore.getCertificateChain(parentSerialNumber);
                    loggerService.print(String.valueOf(chain.length));
                    // Povecaj chain
                    newChain = new java.security.cert.Certificate[chain.length + 1];
                    // Dodaj dete u chain
                    for (int i = 0; i < chain.length; i++) {
                        newChain[i] = chain[i];
                    }
                    newChain[chain.length] = x500Certificate;

                    for (int i = 0; i < newChain.length / 2; i++) {
                        Certificate temp = newChain[i];
                        newChain[i] = newChain[newChain.length - i - 1];
                        newChain[newChain.length - i - 1] = temp;
                    }
                }
                else{
                    newChain = new java.security.cert.Certificate[1];
                    newChain[0] = x500Certificate;
                }
            } else {
                keyStore.load(null, password);
                newChain = new java.security.cert.Certificate[1];
                newChain[0] = x500Certificate;
            }

            if (!isCA){
                path = Paths.get("./keystoreEE.jks");
                if(Files.exists(path)) {
                    keyStore.load(new FileInputStream("keystoreEE.jks"), password);
                } else {
                    keyStore.load(null, password);
                }
            }

            keyStore.setKeyEntry(x500Certificate.getSerialNumber().toString(), privateKey, password, newChain);

            if (isCA){
                keyStore.store(new FileOutputStream("keystoreCA.jks"), password);
            }
            else{
                keyStore.store(new FileOutputStream("keystoreEE.jks"), password);
            }
            loggerService.print("Certificate successfully saved.");

        } catch (KeyStoreException e) {
            loggerService.print("KeyStoreException in function saveKeyStore():" + e.getMessage());
        } catch (NoSuchProviderException e) {
            loggerService.print("NoSuchProviderException in function saveKeyStore():" + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            loggerService.print("NoSuchAlgorithmException in function saveKeyStore():" + e.getMessage());
        } catch (CertificateException e) {
            loggerService.print("CertificateException in function saveKeyStore():" + e.getMessage());
        } catch (FileNotFoundException e) {
            loggerService.print("FileNotFoundException in function saveKeyStore():" + e.getMessage());
        } catch (IOException e) {
            loggerService.print("IOException in function saveKeyStore():" + e.getMessage());
        }
        catch (Exception e) {
            loggerService.print("IOException in function saveKeyStore():" + e.getMessage());
        }

    }

    private List<Object> createSubjectData(X500NameCustom x500NameCustom){
        try {
            KeyPair keyPairSubject = generateKeyPair();

            // REQUIRED
            X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
            builder.addRDN(BCStyle.CN, x500NameCustom.getCommonName());
            builder.addRDN(BCStyle.O, x500NameCustom.getOrganization());
            builder.addRDN(BCStyle.OU, x500NameCustom.getOrganizationalUnit());
            builder.addRDN(BCStyle.L, x500NameCustom.getLocalityCity());
            builder.addRDN(BCStyle.ST, x500NameCustom.getStateProvince());
            builder.addRDN(BCStyle.C, x500NameCustom.getCountryCode());

            // OPTIONAL ATTRIBUTES
            // // // // // // // (AKO NEMA ==> PRAZAN STRING UPISI - MOZDA TREBA IMPLEMENTIRATI OVDE)

            // RANDOM UID AND SERIAL NUMBER
            Random rand = new Random();
            while (systemEntityRepository.getOneByUid(String.valueOf(rand.nextLong())) != null){
                rand = new Random();
            }
            builder.addRDN(BCStyle.UID, String.valueOf(rand.nextLong()));
            // Ovo je vezano za sertifikat - ta jedinstvenost je obezbedjena
            // Generisanje random vrednosti serijskog broja - double - 16 cifara iza nule
            while (certificateRepository.getOneBySerialNumber(String.valueOf(rand.nextLong())) != null){
                rand = new Random();
            }
            builder.addRDN(BCStyle.SERIALNUMBER, String.valueOf(rand.nextLong()));

            // OTHER OPTIONAL
            builder.addRDN(BCStyle.T, x500NameCustom.getTitle());

            builder.addRDN(BCStyle.NAME, x500NameCustom.getName());
            builder.addRDN(BCStyle.SURNAME, x500NameCustom.getSurname());
            builder.addRDN(BCStyle.GIVENNAME, x500NameCustom.getGivenName());
            builder.addRDN(BCStyle.INITIALS, x500NameCustom.getInitials());

            if (!x500NameCustom.getDateOfBirth().equals("")){
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dateOfBirth = dateFormat.parse(x500NameCustom.getDateOfBirth());
                ASN1GeneralizedTime generalizedTime = new ASN1GeneralizedTime(dateOfBirth);
                builder.addRDN(BCStyle.DATE_OF_BIRTH, x500NameCustom.getDateOfBirth());
            }

            builder.addRDN(BCStyle.PLACE_OF_BIRTH, x500NameCustom.getPlaceOfBirth());

            builder.addRDN(BCStyle.GENDER, x500NameCustom.getGender());

            builder.addRDN(BCStyle.COUNTRY_OF_CITIZENSHIP, x500NameCustom.getCountryOfCitizenship());
            builder.addRDN(BCStyle.COUNTRY_OF_RESIDENCE, x500NameCustom.getCountryOfResidence());

            builder.addRDN(BCStyle.E, x500NameCustom.getEmail());
            builder.addRDN(BCStyle.TELEPHONE_NUMBER, x500NameCustom.getTelephoneNumber());

            builder.addRDN(BCStyle.STREET, x500NameCustom.getStreetAddress());
            builder.addRDN(BCStyle.POSTAL_CODE, x500NameCustom.getPostalCode());

            builder.addRDN(BCStyle.BUSINESS_CATEGORY, x500NameCustom.getBusinessCategory());

            builder.addRDN(BCStyle.GENERATION, x500NameCustom.getGeneration());

            List<Object> objects = new ArrayList<Object>();
            objects.add(new SubjectData(keyPairSubject.getPublic(), builder.build()));
            objects.add(keyPairSubject.getPrivate());

            loggerService.print("Generated subject data.");
            return objects;
        } catch (Exception e) {
            loggerService.print("Exception in function createSubjectData()" + e.getMessage());
        }
        return null;
    }

    private KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(2048, random);
            loggerService.print("Generated key pair");
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            loggerService.print(e.getMessage());
        } catch (NoSuchProviderException e) {
            loggerService.print(e.getMessage());
        }
        return null;
    }

}
