package tim15.pki.model;

import sun.security.x509.CertificateSerialNumber;
import sun.security.x509.CertificateVersion;

public class Certificate {

//    private CertificateVersion certificateVersion;
    private Long id;
//    private CertificateSerialNumber serialNumber;
    private boolean isActive;
    private boolean isCA;
    private CertificateStatus certificateStatus;

}
