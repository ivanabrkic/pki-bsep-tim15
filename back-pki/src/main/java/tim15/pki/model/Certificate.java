package tim15.pki.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Certificate {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name="serial_number", nullable = false, unique = true)
    private String serialNumber;

    @Column(name="active", nullable = false)
    private boolean isActive;

    @Column(name="ca", nullable = false)
    private boolean isCA;

    @Column(name="status", nullable = false)
    private CertificateStatus certificateStatus;

    @Column(name="revoke_reason")
    private RevokeReason revokeReason;

    @OneToMany
    @JoinColumn(name = "issuer_certificate", referencedColumnName = "serial_number")
    private Set<Certificate> issuerCertificates;

    @OneToMany
    @JoinColumn(name = "subject_certificate", referencedColumnName = "serial_number")
    private Set<Certificate> subjectCertificates;

    @OneToOne
    @JoinColumn(name="validity_period_id", referencedColumnName = "id", nullable = false)
    private ValidityPeriod validityPeriod;

    @Column(name = "issued_to", nullable = false)
    private String issuedTo;

    @Column(name = "issued_by", nullable = false)
    private String issuedBy;

    public Certificate() {
    }

    public Certificate(Long id, String serialNumber, boolean isActive, boolean isCA, CertificateStatus certificateStatus, RevokeReason revokeReason, String issuer, String subject) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.isActive = isActive;
        this.isCA = isCA;
        this.certificateStatus = certificateStatus;
        this.revokeReason = revokeReason;
        this.issuedTo = issuer;
        this.issuedBy = subject;
    }

    public static CertificateBuilder builder(){
        return new CertificateBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isCA() {
        return isCA;
    }

    public void setCA(boolean CA) {
        isCA = CA;
    }

    public CertificateStatus getCertificateStatus() {
        return certificateStatus;
    }

    public void setCertificateStatus(CertificateStatus certificateStatus) {
        this.certificateStatus = certificateStatus;
    }

    public RevokeReason getRevokeReason() {
        return revokeReason;
    }

    public void setRevokeReason(RevokeReason revokeReason) {
        this.revokeReason = revokeReason;
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "id=" + id +
                ", serialNumber='" + serialNumber + '\'' +
                ", isActive=" + isActive +
                ", isCA=" + isCA +
                ", certificateStatus=" + certificateStatus +
                ", revokeReason=" + revokeReason +
                ", validityPeriod=" + validityPeriod +
                '}';
    }
}
