package tim15.pki.service;

import tim15.pki.model.Certificate;
import tim15.pki.model.enums.CertificateStatus;
import tim15.pki.model.enums.RevokeReason;
import tim15.pki.repository.CertificateRepository;

import java.util.Collection;

public class CertificateRevokeService {
     CertificateRepository certificateRepository;
     LoggerService loggerService;

     public void revokeCertificate(String serialNumber, RevokeReason revokeReason) {
         Certificate certificate = certificateRepository.findOneBySerialNumber(serialNumber);

         if(certificate.getCertificateStatus() != CertificateStatus.REVOKED) {
             certificate.setRevokeReason(revokeReason);
             certificate.setActive(false);
             certificate.setCertificateStatus(CertificateStatus.REVOKED);

             Collection<Certificate> issuedCertificates = certificate.getCertificateChildren();
             for (Certificate issuedCertificate : issuedCertificates) {
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
             loggerService.print("Certificate " + serialNumber + " successfully revoked.");
         }
     }
}
