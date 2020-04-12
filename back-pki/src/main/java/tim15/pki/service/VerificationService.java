package tim15.pki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim15.pki.repository.CertificateRepository;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

@Service
public class VerificationService {

    @Autowired
    private CertificateRepository certificateRepository;

    private boolean checkDate(X509Certificate certificate) {
        Date startDate = certificate.getNotBefore();
        Date endDate = certificate.getNotAfter();
        Date today = new Date();

        return today.after(startDate) && today.before(endDate);
    }

    private boolean verifySignature(X509Certificate certificateToVerify, X509Certificate parentCertificate) {
        try {
            certificateToVerify.verify(parentCertificate.getPublicKey());
            return true;
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return false;
        } catch (SignatureException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        } catch (CertificateException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean verifyActivity(X509Certificate certificate) {
        boolean isActive = certificateRepository.findOneBySerialNumber(certificate.getSerialNumber().toString()).getIsActive();

        boolean isValidDate = checkDate(certificate);
        return isActive && isValidDate;
    }

    private boolean verifyChain(X509Certificate[] certificateList) {
            boolean valid = true;

            for (int i = 0; i < certificateList.length - 1; i++) {
                X509Certificate cer = certificateList[i];
                if (!verifyActivity(cer) || !verifySignature(cer, certificateList[i + 1])) {
                    valid = false;
                }
            }
        return valid;
    }
}
