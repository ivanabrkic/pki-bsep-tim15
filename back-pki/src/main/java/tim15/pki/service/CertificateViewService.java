package tim15.pki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim15.pki.dto.CertificateViewDTO;
import tim15.pki.model.Certificate;
import tim15.pki.model.ValidityPeriod;
import tim15.pki.repository.CertificateRepository;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
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

        String serialNumber = cert.getSerialNumber().toString(16);

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
}
