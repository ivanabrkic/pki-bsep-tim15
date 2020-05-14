package tim15.pki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim15.pki.model.enums.RevokeReason;
import tim15.pki.repository.CertificateRepository;
import tim15.pki.repository.ValidityPeriodRepository;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

@Service
public class AutomatedRevokeService {

    @Autowired
    LoggerService loggerService;

    @Autowired
    VerificationService verificationService;

    @Autowired
    CertificateRevokeService certificateRevokeService;

    @Autowired
    CertificateRepository certificateRepository;

    @Autowired
    ValidityPeriodRepository validityPeriodRepository;

    public RevokeReason catchRevokeReason(Certificate cert, Certificate[] chain, tim15.pki.model.Certificate c){

        loggerService.print("Checking if certificate is invalid in function catchRevokeReason()!");
        if (!verificationService.checkDate((X509Certificate) cert)){
            if(verificationService.expired((X509Certificate) cert)){
                loggerService.print("Certificate status EXPIRED in function catchRevokeReason()!");
                certificateRevokeService.revokeCertificate(c.getSerialNumber(), RevokeReason.EXPIRED);

                return RevokeReason.EXPIRED;
            }
        }
        else if (!verificationService.verifyChain(chain)){
            certificateRevokeService.revokeCertificate(c.getSerialNumber(), RevokeReason.CA_COMPROMISE);
            loggerService.print("Invalid parent certificate in function catchRevokeReason() - CA compromised");

            return RevokeReason.CA_COMPROMISE;
        }
        else if (!verificationService.verifyExpirationChain(chain)){
            certificateRevokeService.revokeCertificate(c.getSerialNumber(), RevokeReason.EXPIRED);
        }
        loggerService.print("Certificate valid!");
        if (!c.getIsActive()){
            c.setIsActive(true);
        }
        certificateRepository.save(c);

        return RevokeReason.NOT_REVOKED;
    }
}
