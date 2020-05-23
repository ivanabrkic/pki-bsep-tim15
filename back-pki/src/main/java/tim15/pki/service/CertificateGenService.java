package tim15.pki.service;

import org.bouncycastle.asn1.ASN1Encodable;
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
import tim15.pki.model.SubjectData;
import tim15.pki.model.SystemEntity;
import tim15.pki.model.ValidityPeriod;
import tim15.pki.model.enums.CertificateStatus;
import tim15.pki.model.enums.EntityType;
import tim15.pki.model.enums.RevokeReason;
import tim15.pki.repository.CertificateRepository;
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
import java.util.*;

@Service
public class CertificateGenService {

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private SystemEntityRepository systemEntityRepository;

    @Autowired
    private ValidityPeriodRepository validityPeriodRepository;

    @Autowired
    private LoggerService loggerService;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private CertificateReaderService certificateReaderService;

    @Autowired
    private AutomatedRevokeService automatedRevokeService;

    public List<tim15.pki.model.Certificate> getAllCAs() throws ParseException {

        List<tim15.pki.model.Certificate> certificates = certificateRepository.findByIsCAAndCertificateStatus(true, CertificateStatus.VALID);
        if (certificates.size() != 0) {
            for (tim15.pki.model.Certificate c : certificates) {
                Certificate cert = certificateReaderService.readCertificate("./keystore/keystoreCA.p12", "bsep", c.getSerialNumber());
                Certificate[] chain = certificateReaderService.readChain("./keystore/keystoreCA.p12", "bsep", c.getSerialNumber());
                automatedRevokeService.catchRevokeReason(cert, chain, c);
            }

            return  certificateRepository.findByIsCAAndCertificateStatus(true, CertificateStatus.VALID);
        }
        return new ArrayList<>();
    }

    public List<SystemEntity> getAllUIDs() {return  systemEntityRepository.findAll();}

    public void generateServerRootCertificate(){
        CertificateGenDTO certGen = new CertificateGenDTO();

        certGen.setEntityType("SERVICE");
        certGen.setIsCA(true);
        certGen.setStartDate(new Date());

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 5);
        Date nextYear = cal.getTime();
        certGen.setEndDate(nextYear);

        certGen.setParentSerialNumber("");

        certGen.setX500NameCustom(tim15.pki.dto.X500NameCustom.builder()
                .setCommonName("front-pki")
                .setOrganization("Public Key Infrastructure")
                .setOrganizationalUnit("Public Key Infrastructure")
                .setStateProvince("Vojvodina")
                .setLocalityCity("Novi Sad")
                .setCountryCode("RS")
                .createX500NameCustom());

        generateCertificate(certGen);
    }

    public void generateTestDataCertificates(){
        CertificateGenDTO certGen = new CertificateGenDTO();

        certGen.setEntityType("SERVICE");
        certGen.setIsCA(true);
        certGen.setStartDate(new Date());

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 5);
        Date nextYear = cal.getTime();
        certGen.setEndDate(nextYear);

        certGen.setParentSerialNumber("");

        certGen.setX500NameCustom(tim15.pki.dto.X500NameCustom.builder()
                .setCommonName("cert-root-1")
                .setOrganization("Public Key Infrastructure")
                .setOrganizationalUnit("Public Key Infrastructure")
                .setStateProvince("Vojvodina")
                .setLocalityCity("Novi Sad")
                .setCountryCode("RS")
                .createX500NameCustom());

        generateCertificate(certGen);

        certGen.setX500NameCustom(tim15.pki.dto.X500NameCustom.builder()
                .setCommonName("cert-root-2")
                .setOrganization("Public Key Infrastructure")
                .setOrganizationalUnit("Public Key Infrastructure")
                .setStateProvince("Vojvodina")
                .setLocalityCity("Novi Sad")
                .setCountryCode("RS")
                .createX500NameCustom());

        generateCertificate(certGen);

        certGen.setX500NameCustom(tim15.pki.dto.X500NameCustom.builder()
                .setCommonName("cert-root-3")
                .setOrganization("Public Key Infrastructure")
                .setOrganizationalUnit("Public Key Infrastructure")
                .setStateProvince("Vojvodina")
                .setLocalityCity("Novi Sad")
                .setCountryCode("RS")
                .createX500NameCustom());

        generateCertificate(certGen);
    }

    public TextMessage generateCertificate(CertificateGenDTO certificateGenDTO) {

        try {
            char [] password = {'b','s','e','p'};
            List<Object> subjectKey =  createSubjectData(certificateGenDTO.getX500NameCustom());

            JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
            builder = builder.setProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

            SubjectData subjectData = (SubjectData) subjectKey.get(0);

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
                // CHECK PARENT VALIDTY
                Certificate cert = certificateReaderService.readCertificate("./keystore/keystoreCA.p12", "bsep", certificateGenDTO.getParentSerialNumber());
                Certificate[] chain = certificateReaderService.readChain("./keystore/keystoreCA.p12", "bsep", certificateGenDTO.getParentSerialNumber());
                loggerService.print("Checking parent validity!");

                if (automatedRevokeService.catchRevokeReason(cert, chain, certificateRepository.findBySerialNumber(certificateGenDTO.getParentSerialNumber())) != RevokeReason.NOT_REVOKED)
                {
                    loggerService.print("Invalid parent certificate in function generateCertificate()");
                    return new TextMessage("Invalid parent certificate!");
                }
                loggerService.print("Parent valid!");
                //
                parentName = certificateReaderService.readIssuerFromStore("./keystore/keystoreCA.p12", certificateGenDTO.getParentSerialNumber(), password, password).getName();
                contentSigner = builder.build(certificateReaderService.readPrivateKey("./keystore/keystoreCA.p12", "bsep", certificateGenDTO.getParentSerialNumber(), "bsep"));
                issuerPublicKey = certificateReaderService.readCertificate("./keystore/keystoreCA.p12", "bsep", certificateGenDTO.getParentSerialNumber()).getPublicKey();
                issuerCommonName = parentName.getRDNs(BCStyle.CN)[0].getFirst().getValue().toString();
            }

            /// GENERISANJE RANDOM SERIJSKOG BROJA SERTIFIKATA
            Random rand = new Random();
            Long serialNumber = rand.nextLong();
            while (certificateRepository.findBySerialNumber(String.valueOf(Math.abs(serialNumber))) != null){
                serialNumber = rand.nextLong();
            }

            System.out.println(Math.abs(serialNumber));

            X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(parentName,
                    new BigInteger(String.valueOf(Math.abs(serialNumber))),
                    certificateGenDTO.getStartDate(),
                    certificateGenDTO.getEndDate(),
                    subjectData.getName(),
                    subjectData.getPublicKey());

            ///////////// EKSTENZIJE////////////////
            BasicConstraints basicConstraints = new BasicConstraints(certificateGenDTO.getIsCA());
            certGen.addExtension(new ASN1ObjectIdentifier("2.5.29.19"), true, basicConstraints);

            JcaX509ExtensionUtils extensionUtils = new JcaX509ExtensionUtils();
            AuthorityKeyIdentifier authorityKeyIdentifier = extensionUtils
                    .createAuthorityKeyIdentifier(issuerPublicKey);
            certGen.addExtension(new ASN1ObjectIdentifier("2.5.29.35"), false, authorityKeyIdentifier);

            SubjectKeyIdentifier subjectKeyIdentifier = extensionUtils
                    .createSubjectKeyIdentifier(subjectData.getPublicKey());
            certGen.addExtension(new ASN1ObjectIdentifier("2.5.29.14"), false, subjectKeyIdentifier);

            ///// POTPISIVANJE
            X509CertificateHolder certHolder = certGen.build(contentSigner);

            JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
            certConverter = certConverter.setProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

            X509Certificate certificate = certConverter.getCertificate(certHolder);

            //////// Priprema liste za slanje funkciji za cuvanje u keystore
            List<Object> certificateKeySerialIsCA = new ArrayList<>();
            certificateKeySerialIsCA.add(certificate);
            certificateKeySerialIsCA.add(subjectKey.get(1));
            certificateKeySerialIsCA.add(certificateGenDTO.getParentSerialNumber());
            certificateKeySerialIsCA.add(certificateGenDTO.getIsCA());

            loggerService.print("Certificate successfully created.");

            saveKeyStore(certificateKeySerialIsCA, password, certificateGenDTO, issuerCommonName);

            return new TextMessage("Certificate successfully created and saved in database and keystore.");

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
        } catch (Exception e) {
            loggerService.print("Exception in function generateCertificate():" + e.getMessage());
        }

        loggerService.print("Certificate failed to create and save.");
        return new TextMessage("Certificate failed to create and save.");
    }

    // NULL - situacija - saljem intermediate sa izabranim roditeljem
    private void saveDatabase(List<Object> certificateKeySerialIsCA, String serialNumber, ValidityPeriod validityPeriod, String subjectName, String issuerName, EntityType entityType, boolean notActive){
        try {
            tim15.pki.model.Certificate certificate = tim15.pki.model.Certificate.builder().setIsActive(!notActive)
                    .setIsCA((Boolean) certificateKeySerialIsCA.get(3))
                    .setIssuedTo(subjectName)
                    .setIssuedBy(issuerName)
                    .setCertificateStatus(CertificateStatus.VALID)
                    .setSerialNumber(serialNumber)
                    .setRevokeReason(RevokeReason.NOT_REVOKED)
                    .setEntityType(entityType)
                    .createCertificate();

            certificate.setCertificateChildren(new HashSet<>());

            if (certificateKeySerialIsCA.get(2).toString().equals("")) {
                certificate.setCertificateParents(null);
            } else {
                certificate.setCertificateParents(new HashSet<>());
                tim15.pki.model.Certificate parent = certificateRepository.findBySerialNumber(certificateKeySerialIsCA.get(2).toString());
                certificate.getCertificateParents().add(parent);
                parent.getCertificateChildren().add(certificate);
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

    private void saveKeyStore(List<Object> certificateKeySerialIsCA, char[] password, CertificateGenDTO certificateGenDTO, String issuerCommonName){
            try {

            X509Certificate x500Certificate = (X509Certificate) certificateKeySerialIsCA.get(0);
            PrivateKey privateKey = (PrivateKey) certificateKeySerialIsCA.get(1);
            String parentSerialNumber = (String) certificateKeySerialIsCA.get(2);
            boolean isCA = (Boolean) certificateKeySerialIsCA.get(3);

            // proveri jos jednom validnost datuma pre cuvanja u keystore
            if (verificationService.expired(x500Certificate)){
                loggerService.print("Invalid certificate validity date!");
                return;
            }

            KeyStore keyStore = KeyStore.getInstance("PKCS12");

            java.security.cert.Certificate[] newChain;

            Path path = Paths.get("./keystore/keystoreCA.p12");
            if(Files.exists(path)) {
                keyStore.load(new FileInputStream("./keystore/keystoreCA.p12"), password);
                // Preuzmi chain roditelja
                if (!parentSerialNumber.equals("")) {
                    java.security.cert.Certificate[] chain = keyStore.getCertificateChain(parentSerialNumber);
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
                path = Paths.get("./keystore/keystoreEE.p12");
                if(Files.exists(path)) {
                    keyStore.load(new FileInputStream("./keystore/keystoreEE.p12"), password);
                } else {
                    keyStore.load(null, password);
                }
            }

            keyStore.setKeyEntry(x500Certificate.getSerialNumber().toString(), privateKey, password, newChain);

            if (isCA){
                keyStore.store(new FileOutputStream("./keystore/keystoreCA.p12"), password);
            }
            else{
                keyStore.store(new FileOutputStream("./keystore/keystoreEE.p12"), password);
            }
            loggerService.print("Certificate successfully saved in keystore.");

            boolean notActive = verificationService.notActive(x500Certificate);
            ValidityPeriod validityPeriod = new ValidityPeriod(certificateGenDTO.getStartDate(), certificateGenDTO.getEndDate());
            saveDatabase(certificateKeySerialIsCA, x500Certificate.getSerialNumber().toString(), validityPeriod, certificateGenDTO.getX500NameCustom().getCommonName(), issuerCommonName, EntityType.toEnum(certificateGenDTO.getEntityType()), notActive);

        } catch (KeyStoreException e) {
            loggerService.print("KeyStoreException in function saveKeyStore():" + e.getMessage());
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
            // GENERISANJE PAROVA KLJUCEVA ZA SUBJEKTA
            KeyPair keyPairSubject = generateKeyPair();

            // DODELJIVANJE VREDNOSTI ATRIBUTIMA
            X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);

            if (x500NameCustom.getCommonName() != null) {
                if (!x500NameCustom.getCommonName().equals("")) {
                    builder.addRDN(BCStyle.CN, x500NameCustom.getCommonName());
                }
            }

            if (x500NameCustom.getOrganization() != null) {
                if (!x500NameCustom.getOrganization().equals("")) {
                    builder.addRDN(BCStyle.O, x500NameCustom.getOrganization());
                }
            }

            if (x500NameCustom.getOrganizationalUnit() != null) {
                if (!x500NameCustom.getOrganizationalUnit().equals("")) {
                    builder.addRDN(BCStyle.OU, x500NameCustom.getOrganizationalUnit());
                }
            }

            if (x500NameCustom.getLocalityCity() != null) {
                if (!x500NameCustom.getLocalityCity().equals("")) {
                    builder.addRDN(BCStyle.L, x500NameCustom.getLocalityCity());
                }
            }

            if (x500NameCustom.getStateProvince() != null) {
                if (!x500NameCustom.getStateProvince().equals("")) {
                    builder.addRDN(BCStyle.ST, x500NameCustom.getStateProvince());
                }
            }


            if (x500NameCustom.getCountryCode() != null) {
                if (!x500NameCustom.getCountryCode().equals("")) {
                    builder.addRDN(BCStyle.C, x500NameCustom.getCountryCode());
                }
            }

            // ADDITIONAL ATTRIBUTES
            // RANDOM UID
            if (x500NameCustom.getUid() != null){
                if (x500NameCustom.getUid().equals("newUID")){
                    Random rand = new Random();
                    Long uid = rand.nextLong();
                    while (systemEntityRepository.findByUid(String.valueOf(Math.abs(uid))) != null){
                        uid = rand.nextLong();
                    }
                    builder.addRDN(BCStyle.UID, String.valueOf(Math.abs(uid)));
                    SystemEntity systemEntity = new SystemEntity();
                    systemEntity.setUid(String.valueOf(Math.abs(uid)));
                    systemEntityRepository.save(systemEntity);

                    loggerService.print("..............................NEW UID DONE.............................");
                }
                else if (!x500NameCustom.getUid().equals("")){
                    if(systemEntityRepository.findByUid(x500NameCustom.getUid()) == null){
                        return null;
                    }
                    builder.addRDN(BCStyle.UID, (ASN1Encodable) systemEntityRepository.findByUid(x500NameCustom.getUid()));
                }
            }

            if (x500NameCustom.getSerialNumber() != null) {
                if (!x500NameCustom.getSerialNumber().equals("")) {
                    builder.addRDN(BCStyle.SERIALNUMBER, x500NameCustom.getSerialNumber());
                }
            }


            if (x500NameCustom.getTitle() != null) {
                if (!x500NameCustom.getTitle().equals("")) {
                    builder.addRDN(BCStyle.T, x500NameCustom.getTitle());
                }
            }

            if (x500NameCustom.getName() != null) {
                if (!x500NameCustom.getName().equals("")) {
                    builder.addRDN(BCStyle.NAME, x500NameCustom.getName());
                }
            }

            if (x500NameCustom.getSurname() != null) {
                if (!x500NameCustom.getSurname().equals("")) {
                    builder.addRDN(BCStyle.SURNAME, x500NameCustom.getSurname());
                }
            }

            if (x500NameCustom.getGivenName() != null) {
                if (!x500NameCustom.getGivenName().equals("")) {
                    builder.addRDN(BCStyle.GIVENNAME, x500NameCustom.getGivenName());
                }
            }

            if (x500NameCustom.getInitials() != null) {
                if (!x500NameCustom.getInitials().equals("")) {
                    builder.addRDN(BCStyle.INITIALS, x500NameCustom.getInitials());
                }
            }

            if (x500NameCustom.getDateOfBirth() != null){
                if (!x500NameCustom.getDateOfBirth().equals("")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDD000000Z");
                    builder.addRDN(BCStyle.DATE_OF_BIRTH, sdf.format(x500NameCustom.getDateOfBirth()));
                }
            }

            if (x500NameCustom.getPlaceOfBirth() != null) {
                if (!x500NameCustom.getPlaceOfBirth().equals("")) {
                    builder.addRDN(BCStyle.PLACE_OF_BIRTH, x500NameCustom.getPlaceOfBirth());
                }
            }

            if (x500NameCustom.getGender() != null) {
                if (!x500NameCustom.getGender().equals("")) {
                    builder.addRDN(BCStyle.GENDER, x500NameCustom.getGender());
                }
            }

            if (x500NameCustom.getCountryOfCitizenship() != null) {
                if (!x500NameCustom.getCountryOfCitizenship().equals("")) {
                    builder.addRDN(BCStyle.COUNTRY_OF_CITIZENSHIP, x500NameCustom.getCountryOfCitizenship());
                }
            }

            if (x500NameCustom.getCountryOfResidence() != null) {
                if (!x500NameCustom.getCountryOfResidence().equals("")) {
                    builder.addRDN(BCStyle.COUNTRY_OF_RESIDENCE, x500NameCustom.getCountryOfResidence());
                }
            }

            if (x500NameCustom.getEmail() != null) {
                if (!x500NameCustom.getEmail().equals("")) {
                    builder.addRDN(BCStyle.E, x500NameCustom.getEmail());
                }
            }

            if (x500NameCustom.getTelephoneNumber() != null) {
                if (!x500NameCustom.getTelephoneNumber().equals("")) {
                    builder.addRDN(BCStyle.TELEPHONE_NUMBER, x500NameCustom.getTelephoneNumber());
                }
            }

            if (x500NameCustom.getStreetAddress() != null) {
                if (!x500NameCustom.getStreetAddress().equals("")) {
                    builder.addRDN(BCStyle.STREET, x500NameCustom.getStreetAddress());
                }
            }

            if (x500NameCustom.getPostalCode() != null) {
                if (!x500NameCustom.getPostalCode().equals("")) {
                    builder.addRDN(BCStyle.POSTAL_CODE, x500NameCustom.getPostalCode());
                }
            }

            if (x500NameCustom.getBusinessCategory() != null) {
                if (!x500NameCustom.getBusinessCategory().equals("")) {
                    builder.addRDN(BCStyle.BUSINESS_CATEGORY, x500NameCustom.getBusinessCategory());
                }
            }

            if (x500NameCustom.getGeneration() != null) {
                if (!x500NameCustom.getGeneration().equals("")) {
                    builder.addRDN(BCStyle.GENERATION, x500NameCustom.getGeneration());
                }
            }

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
