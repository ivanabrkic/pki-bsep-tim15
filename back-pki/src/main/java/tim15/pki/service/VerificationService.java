package tim15.pki.service;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim15.pki.repository.CertificateRepository;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

@Service
public class VerificationService {

    @Autowired
    private CertificateRepository certificateRepository;

    public boolean expired(X509Certificate certificate) {

        LocalDate endDate = new LocalDate(certificate.getNotAfter());
        LocalDate today = new LocalDate(new Date());

        return today.compareTo(endDate) > 0;
    }

    public boolean notActive(X509Certificate certificate) {
        LocalDate startDate = new LocalDate(certificate.getNotBefore());
        LocalDate today = new LocalDate(new Date());

        return today.compareTo(startDate) < 0;
    }

    public boolean checkDate(X509Certificate certificate) {
        LocalDate startDate = new LocalDate(certificate.getNotBefore());
        LocalDate endDate = new LocalDate(certificate.getNotAfter());
        LocalDate today = new LocalDate(new Date());

        return today.compareTo(startDate) >= 0 && today.compareTo(endDate) <= 0;
    }

    public boolean verifySignature(Certificate certificateToVerify, Certificate parentCertificate) {
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

    public boolean verifyActivity(X509Certificate certificate) {
        boolean isActive = certificateRepository.findBySerialNumber(certificate.getSerialNumber().toString()).getIsActive();

        return isActive;
    }

    public boolean verifyChain(Certificate[] certificateList) {
        boolean valid = true;

        for (int i = 0; i < certificateList.length - 1; i++) {
            Certificate cer = certificateList[i];
            if (!verifySignature(cer, certificateList[i + 1])) {
                valid = false;
            }
        }
        return valid;
    }

    public boolean verifyExpirationChain(Certificate[] certificateList) {
        boolean valid = true;

        for (int i = 0; i < certificateList.length - 1; i++) {
            Certificate cer = certificateList[i];
            if (expired((X509Certificate) certificateList[i])) {
                valid = false;
            }
        }
        return valid;
    }

}
