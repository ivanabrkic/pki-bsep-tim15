package tim15.pki.service;

import tim15.pki.model.Certificate;
import tim15.pki.model.enums.CertificateStatus;
import tim15.pki.model.enums.RevokeReason;
import tim15.pki.repository.CertificateRepository;

public class CertificateRevokeService {
     CertificateRepository certificateRepository;
     LoggerService loggerService;

     public void revokeCertificate(String serialNumber, RevokeReason revokeReason) {
         Certificate certificate = certificateRepository.findOneBySerialNumber(serialNumber);

         if(certificate.getCertificateStatus() != CertificateStatus.REVOKED) {
             certificate.setRevokeReason(revokeReason);
             certificate.setActive(false);
             certificate.setCertificateStatus(CertificateStatus.REVOKED);
//// Izmenila sam ti kod ovde Milane zbog gresaka
             for (Certificate issuedCertificate : certificate.getSubjectCertificates()) {
                 String issuedCertificateSerial = issuedCertificate.getSerialNumber();
                 switch (revokeReason) {
                     case EXPIRED:
                         revokeCertificate(issuedCertificateSerial, RevokeReason.CERTIFICATE_HOLD);
                         break;
                     case KEY_COMPROMISE:
                         revokeCertificate(issuedCertificateSerial, RevokeReason.CA_COMPROMISE);
                         break;
                     default:
                         revokeCertificate(issuedCertificateSerial, RevokeReason.UNKNOWN);
                         break;
                 }
             }

             certificateRepository.save(certificate);
             loggerService.print("Certificate " + serialNumber + " successfully revoked.");
         }
     }
}
