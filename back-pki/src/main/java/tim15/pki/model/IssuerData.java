package tim15.pki.model;

import sun.security.x509.CertificateIssuerName;
import sun.security.x509.X500Name;

import java.security.PrivateKey;

public class IssuerData {

//    CertificateIssuerName
    private X500Name name;
    private PrivateKey privateKey;
}
