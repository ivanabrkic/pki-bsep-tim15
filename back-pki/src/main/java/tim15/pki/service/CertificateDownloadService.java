package tim15.pki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim15.pki.dto.CertificateDownloadDTO;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchProviderException;
import java.security.cert.X509Certificate;

@Service
public class CertificateDownloadService {
    @Autowired
    private LoggerService loggerService;

    //UBACI EXEPTIONE
    //ENKRIPTOVATI PRE SLANJA
    public void downloadCertificate(String serialNumber) {
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS","SUN");
            X509Certificate certificate = (X509Certificate)keyStore.getCertificate(serialNumber);
            FileOutputStream outputStream = new FileOutputStream("download_certificate_"+serialNumber+".cer");
            //outputStream.write();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            loggerService.print(e.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
