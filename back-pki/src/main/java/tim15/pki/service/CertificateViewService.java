package tim15.pki.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStrictStyle;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim15.pki.dto.CertificateDetailsDTO;
import tim15.pki.dto.CertificateViewDTO;
import tim15.pki.model.Certificate;
import tim15.pki.model.ValidityPeriod;
import tim15.pki.repository.CertificateRepository;

import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;



@Service
public class CertificateViewService {


    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private CertificateReaderService certificateReaderService;
    /*
    * Getting one specific keyStore,
    * params are key store path and password for keystore
    *
    *
    * */
    public KeyStore getKeyStore(String keyStorePath, String keyStorePassword) throws CertificateException, NoSuchAlgorithmException, IOException, KeyStoreException, NoSuchProviderException {
        char[] keyStorePassArray = keyStorePassword.toCharArray();
        String keyStoreFileName = "";
        if (keyStorePath.equals("ca")) {
             keyStoreFileName = "./keystore/keystoreCA.jks";
        } else if (keyStorePath.equals("end-entity")) {
             keyStoreFileName = "./keystore/keystoreEE.jks";
        } else {
            System.out.println("nije dobio dobar parametar sa fronta!");
        }
        KeyStore ks = KeyStore.getInstance("JKS", "SUN");
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFileName));
            ks.load(in, keyStorePassArray);
        } catch (FileNotFoundException e) {
            ks.load(null, keyStorePassArray);
        }

        return ks;
    }

    /*
    * Getting all Certificates of
    * 1 role from keystore
    *
    * */
    public List<X509Certificate> getCertificates(String role, String keyStorePassword) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, NoSuchProviderException, IOException {

        List<X509Certificate> certificates = new ArrayList<>();
        String keyStorePath = role;
        try {
            KeyStore ks = getKeyStore(keyStorePath, keyStorePassword);

            Enumeration<String> aliases = ks.aliases();
            while (aliases.hasMoreElements()) {
                certificates.add((X509Certificate) ks.getCertificate(aliases.nextElement()));
            }
            return certificates;
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            return null;
        }
    }
    /*
    * Creating CertificateDTO for showing it in
    * table view on web page
    *
    * */
    public CertificateViewDTO createCertificateDTO(X509Certificate cert) {
        CertificateViewDTO certDTO = new CertificateViewDTO();

        String serialNumber = String.valueOf(cert.getSerialNumber());

        Certificate databaseCertificate = certificateRepository.findBySerialNumber(serialNumber);

        certDTO.setIssuerName(databaseCertificate.getIssuedBy());
        certDTO.setSubjectName(databaseCertificate.getIssuedTo());

        ValidityPeriod vp = new ValidityPeriod(cert.getNotBefore(),cert.getNotAfter());

        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");

        String date1 = dateFormat.format(vp.getStartDate());
        String date2 = dateFormat.format(vp.getEndDate());


        certDTO.setValidFrom(date1);
        certDTO.setValidTo(date2);
        certDTO.setSerialNumber(serialNumber);

        return certDTO;
    }
    /*
    * Converting list of X509Certificate to
    * list of CertificateViewDTO
    *
    * */
    public List<CertificateViewDTO> convertCertificatesToDTOList(List<X509Certificate> certs) {
        List<CertificateViewDTO> DTOList = new ArrayList<CertificateViewDTO>();

        for(X509Certificate cert: certs) {

            CertificateViewDTO dto = createCertificateDTO(cert);
            DTOList.add(dto);
        }
        return DTOList;
    }

    public X509Certificate getCertificate(String role, String keyStorePassword, String alias) {
        try {
            //kreiramo instancu KeyStore
            KeyStore ks = getKeyStore(role, keyStorePassword);

            if(ks.isKeyEntry(alias)) {
                X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
                return cert;
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CertificateDetailsDTO getDetails(String serialNumber) throws CertificateEncodingException {
        CertificateDetailsDTO cdd = new CertificateDetailsDTO();
        Certificate certificateDatabase = certificateRepository.findBySerialNumber(serialNumber);
        String ca = certificateDatabase.getIsCA() ? "ca" : "end-entity";
        X509Certificate fromKeyStore = getCertificate(ca, "bsep", serialNumber);

        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");

        String date1 = dateFormat.format(fromKeyStore.getNotBefore());
        String date2 = dateFormat.format(fromKeyStore.getNotAfter());


        X500Name issuerName = new JcaX509CertificateHolder((X509Certificate) fromKeyStore).getIssuer();
        X500Name subjectName = new JcaX509CertificateHolder((X509Certificate) fromKeyStore).getSubject();

        cdd.setSerialNumber(certificateDatabase.getSerialNumber());
        cdd.setValidFrom(date1);
        cdd.setValidTo(date2);
        cdd.setIsCa(certificateDatabase.getIsCA() ? "Yes" : "No");
        cdd.setSigAlgName("sha256RSA");
        cdd.setIssuerCN(issuerName.getRDNs(BCStrictStyle.CN)[0].getFirst().getValue().toString());
        cdd.setIssuerOrganization(issuerName.getRDNs(BCStrictStyle.O)[0].getFirst().getValue().toString());
        cdd.setIssuerOrganizationalUnit(issuerName.getRDNs(BCStrictStyle.OU)[0].getFirst().getValue().toString());
        cdd.setIssuerLocalityCity(issuerName.getRDNs(BCStrictStyle.L)[0].getFirst().getValue().toString());
        cdd.setIssuerStateProvince(issuerName.getRDNs(BCStrictStyle.ST)[0].getFirst().getValue().toString());
        cdd.setIssuerCountryCode(issuerName.getRDNs(BCStrictStyle.C)[0].getFirst().getValue().toString());

        cdd.setSubjectCN(subjectName.getRDNs(BCStrictStyle.CN)[0].getFirst().getValue().toString());
        cdd.setSubjectOrganization(subjectName.getRDNs(BCStrictStyle.O)[0].getFirst().getValue().toString());
        cdd.setSubjectOrganizationalUnit(subjectName.getRDNs(BCStrictStyle.OU)[0].getFirst().getValue().toString());
        cdd.setSubjectLocalityCity(subjectName.getRDNs(BCStrictStyle.L)[0].getFirst().getValue().toString());
        cdd.setSubjectStateProvince(subjectName.getRDNs(BCStrictStyle.ST)[0].getFirst().getValue().toString());
        cdd.setSubjectCountryCode(subjectName.getRDNs(BCStrictStyle.C)[0].getFirst().getValue().toString());

        if (subjectName.getRDNs(BCStrictStyle.UID).length > 0){
            cdd.setSubjectUID(checkString(subjectName.getRDNs(BCStrictStyle.UID)[0].getFirst().getValue().toString()));
        }

        if (subjectName.getRDNs(BCStrictStyle.T).length > 0) {
            cdd.setSubjectTitle(checkString(subjectName.getRDNs(BCStrictStyle.T)[0].getFirst().getValue().toString()));
        }

        if (subjectName.getRDNs(BCStrictStyle.NAME).length > 0) {
            cdd.setSubjectName(checkString(subjectName.getRDNs(BCStrictStyle.NAME)[0].getFirst().getValue().toString()));
        }

        if (subjectName.getRDNs(BCStrictStyle.SURNAME).length > 0) {
            cdd.setSubjectSurname(checkString(subjectName.getRDNs(BCStrictStyle.SURNAME)[0].getFirst().getValue().toString()));
        }

        if (subjectName.getRDNs(BCStrictStyle.GIVENNAME).length > 0) {
            cdd.setSubjectGivenName(checkString(subjectName.getRDNs(BCStrictStyle.GIVENNAME)[0].getFirst().getValue().toString()));
        }

        if (subjectName.getRDNs(BCStrictStyle.INITIALS).length > 0) {
            cdd.setSubjectInitials(checkString(subjectName.getRDNs(BCStrictStyle.INITIALS)[0].getFirst().getValue().toString()));
        }

        if (subjectName.getRDNs(BCStrictStyle.DATE_OF_BIRTH).length > 0){
            cdd.setSubjectDateOfBirth(checkString(subjectName.getRDNs(BCStrictStyle.DATE_OF_BIRTH)[0].getFirst().getValue().toString()));
        }

        if (subjectName.getRDNs(BCStrictStyle.PLACE_OF_BIRTH).length > 0) {
            cdd.setSubjectPlaceOfBirth(checkString(subjectName.getRDNs(BCStrictStyle.PLACE_OF_BIRTH)[0].getFirst().getValue().toString()));
        }

        if (subjectName.getRDNs(BCStrictStyle.GENDER).length > 0) {
            cdd.setSubjectGender(checkString(subjectName.getRDNs(BCStrictStyle.GENDER)[0].getFirst().getValue().toString()));
        }

        if (subjectName.getRDNs(BCStrictStyle.COUNTRY_OF_CITIZENSHIP).length > 0) {
            cdd.setSubjectCountryOfCitizenship(checkString(subjectName.getRDNs(BCStrictStyle.COUNTRY_OF_CITIZENSHIP)[0].getFirst().getValue().toString()));
        }

        if (subjectName.getRDNs(BCStrictStyle.COUNTRY_OF_RESIDENCE).length > 0) {
            cdd.setSubjectCountryOfResidence(checkString(subjectName.getRDNs(BCStrictStyle.COUNTRY_OF_RESIDENCE)[0].getFirst().getValue().toString()));
        }

        if (subjectName.getRDNs(BCStrictStyle.E).length > 0) {
            cdd.setSubjectEmail(checkString(subjectName.getRDNs(BCStrictStyle.E)[0].getFirst().getValue().toString()));
        }

        if (subjectName.getRDNs(BCStrictStyle.TELEPHONE_NUMBER).length > 0) {
            cdd.setSubjectTelephoneNumber(checkString(subjectName.getRDNs(BCStrictStyle.TELEPHONE_NUMBER)[0].getFirst().getValue().toString()));
        }

        if (subjectName.getRDNs(BCStrictStyle.STREET).length > 0) {
            cdd.setSubjectStreet(checkString(subjectName.getRDNs(BCStrictStyle.STREET)[0].getFirst().getValue().toString()));
        }

        if (subjectName.getRDNs(BCStrictStyle.POSTAL_CODE).length > 0) {
            cdd.setSubjectPostalCode(checkString(subjectName.getRDNs(BCStrictStyle.POSTAL_CODE)[0].getFirst().getValue().toString()));
        }

        if (subjectName.getRDNs(BCStrictStyle.BUSINESS_CATEGORY).length > 0) {
            cdd.setSubjectBusinessCategory(checkString(subjectName.getRDNs(BCStrictStyle.BUSINESS_CATEGORY)[0].getFirst().getValue().toString()));
        }

        if (subjectName.getRDNs(BCStrictStyle.GENERATION).length > 0) {
            cdd.setSubjectGeneration(checkString(subjectName.getRDNs(BCStrictStyle.GENERATION)[0].getFirst().getValue().toString()));
        }

        return cdd;
    }

    public String checkString(String stringToCheck) {
        if (stringToCheck == null) {
            stringToCheck = "";
        }
        return stringToCheck;
    }

    public String download(String serialNumber) throws IOException, CertificateEncodingException {
        Certificate certificateDatabase = certificateRepository.findBySerialNumber(serialNumber);
        String ca = certificateDatabase.getIsCA() ? "ca" : "end-entity";
        java.security.cert.Certificate fromKeyStore = (java.security.cert.Certificate) getCertificate(ca, "bsep", serialNumber);

        String Path = "..//front-pki/src/assets/certificates/";
        String file = Path + ca + "_" + serialNumber + ".cer";
        String fileFrontend = "assets/certificates/" + ca + "_" + serialNumber + ".cer";
        FileOutputStream os = new FileOutputStream(file);
        os.write("-----BEGIN CERTIFICATE-----\n".getBytes("US-ASCII"));
        os.write(Base64.encodeBase64(fromKeyStore.getEncoded(), true));
        os.write("-----END CERTIFICATE-----\n".getBytes("US-ASCII"));
        os.close();

        return fileFrontend;
    }
}
