package tim15.pki.service;

import tim15.pki.dto.CertificateGenDTO;
import tim15.pki.dto.TextMessage;
import tim15.pki.model.Certificate;
import tim15.pki.model.enums.CertificateStatus;
import tim15.pki.model.enums.RevokeReason;
import tim15.pki.repository.CertificateRepository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.util.Collection;

public class CertificateRevokeService {
     CertificateRepository certificateRepository;
     LoggerService loggerService;

     public TextMessage revokeCertificate(String serialNumber, RevokeReason revokeReason) {
         Certificate certificate = certificateRepository.findOneBySerialNumber(serialNumber);

         if(certificate.getCertificateStatus() != CertificateStatus.REVOKED) {
             certificate.setRevokeReason(revokeReason);
             certificate.setIsActive(false);
             certificate.setCertificateStatus(CertificateStatus.REVOKED);

             Collection<Certificate> issuedCertificates = certificate.getCertificateChildren();
             for (Certificate issuedCertificate : issuedCertificates) {
                 this.revokeFromKeyStore(issuedCertificate.getSerialNumber());
                 switch (issuedCertificate.getRevokeReason()) {
                     case EXPIRED:
                         revokeCertificate(issuedCertificate.getSerialNumber(), RevokeReason.CERTIFICATE_HOLD);
                         break;
                     case KEY_COMPROMISE:
                         revokeCertificate(issuedCertificate.getSerialNumber(), RevokeReason.CA_COMPROMISE);
                         break;
                     default:
                         revokeCertificate(issuedCertificate.getSerialNumber(), RevokeReason.UNKNOWN);
                         break;
                 }
             }

             certificateRepository.save(certificate);
             this.revokeFromKeyStore(certificate.getSerialNumber());
             loggerService.print("Certificate " + serialNumber + " successfully revoked.");
             return new TextMessage("Certificate " + serialNumber + " successfully revoked.");
         }
         else {
             loggerService.print("Certificate " + serialNumber + " revocation failed.");
             return new TextMessage("Certificate " + serialNumber + " revocation failed..");
         }
        }

        private void revokeFromKeyStore(String serialNumber) {
            String alias = null;
            try {
                KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");
                keyStore.deleteEntry(serialNumber);
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                loggerService.print(e.getMessage());
            }
    }
}
