package tim15.pki.service;

import org.springframework.stereotype.Service;
import tim15.pki.dto.CertificateViewDTO;
import tim15.pki.model.Certificate;
import tim15.pki.model.ValidityPeriod;

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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Service
public class CertificateViewService {

    /*
    * Getting one specific keyStore,
    * params are key store path and password for keystore
    *
    *
    * */
    public KeyStore getKeyStore(String keyStorePath, String keyStorePassword) throws CertificateException, NoSuchAlgorithmException, IOException, KeyStoreException, NoSuchProviderException {
        char[] keyStorePassArray = keyStorePassword.toCharArray();

        KeyStore ks = KeyStore.getInstance("JKS", "SUN");
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStorePath));
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

        certDTO.setIssuerName(cert.getIssuerX500Principal().getName());
        certDTO.setSubjectName(cert.getSubjectX500Principal().getName());

        ValidityPeriod vp = new ValidityPeriod(cert.getNotBefore(),cert.getNotAfter());

        certDTO.setValidityPeriod(vp);
        certDTO.setSerialNumber(cert.getSerialNumber().toString(16));

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
