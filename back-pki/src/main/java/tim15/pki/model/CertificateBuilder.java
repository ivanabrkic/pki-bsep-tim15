package tim15.pki.model;

import tim15.pki.model.enums.CertificateStatus;
import tim15.pki.model.enums.RevokeReason;

public class CertificateBuilder {
    private String certificateVersion;
    private Long id;
    private String serialNumber;
    private boolean isActive;
    private boolean isCA;
    private CertificateStatus certificateStatus;
    private RevokeReason revokeReason;
    private String issuedTo;
    private String issuedBy;

    public CertificateBuilder setCertificateVersion(String certificateVersion) {
        this.certificateVersion = certificateVersion;
        return this;
    }

    public CertificateBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public CertificateBuilder setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public CertificateBuilder setIsActive(boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public CertificateBuilder setIsCA(boolean isCA) {
        this.isCA = isCA;
        return this;
    }

    public CertificateBuilder setCertificateStatus(CertificateStatus certificateStatus) {
        this.certificateStatus = certificateStatus;
        return this;
    }

    public CertificateBuilder setRevokeReason(RevokeReason revokeReason) {
        this.revokeReason = revokeReason;
        return this;
    }

    public CertificateBuilder setIssuer(String issuer) {
        this.issuedTo = issuer;
        return this;
    }

    public CertificateBuilder setSubject(String subject) {
        this.issuedBy = subject;
        return this;
    }

    public Certificate createCertificate() {
        return new Certificate(id, serialNumber, isActive, isCA, certificateStatus, revokeReason, issuedTo, issuedBy);
    }
}
