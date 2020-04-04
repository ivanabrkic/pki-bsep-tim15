package tim15.pki.model;

import tim15.pki.model.enums.CertificateStatus;
import tim15.pki.model.enums.RevokeReason;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Certificate {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name="version", nullable = false)
    private String certificateVersion;

    @Column(name="serial_number", nullable = false, unique = true)
    private String serialNumber;

    @Column(name="active", nullable = false)
    private boolean isActive;

    @Column(name="ca", nullable = false)
    private boolean isCA;

    @Column(name="status", nullable = false)
    private CertificateStatus certificateStatus;

    //PROMENIO NULABLE SAM NA TRUE UKOLIKO NECEMO PRAVITI KLASU ZA REVOKOVANE ONDA CE NAM BITI NULL AKO NIJE REVOKOVAN
    @Column(name="revoke_reason")
    private RevokeReason revokeReason;

    @OneToMany
    @JoinColumn(name = "extension", referencedColumnName = "id")
    private Set<Extension> extensions;

    @OneToOne
    @JoinColumn(name="validity_period_id", referencedColumnName = "id", nullable = false)
    private ValidityPeriod validityPeriod;

    @Column(name = "issuer", nullable = false)
    private String issuer;

    @Column(name = "subject", nullable = false)
    private String subject;

    @OneToMany
    @JoinColumn(name="serial_number_owners", referencedColumnName = "serial_number")
    private Set<Certificate> certificationPath;

    @Column(name = "issued_cetificates")
    private Set<String> issuedCertificates;

    public Certificate() {
    }

    public Certificate(String certificateVersion, Long id, String serialNumber, boolean isActive, boolean isCA, CertificateStatus certificateStatus, RevokeReason revokeReason, String issuer, String subject) {
        this.certificateVersion = certificateVersion;
        this.id = id;
        this.serialNumber = serialNumber;
        this.isActive = isActive;
        this.isCA = isCA;
        this.certificateStatus = certificateStatus;
        this.revokeReason = revokeReason;
        this.issuer = issuer;
        this.subject = subject;
    }

    public static CertificateBuilder builder(){
        return new CertificateBuilder();
    }

    public String getCertificateVersion() {
        return certificateVersion;
    }

    public void setCertificateVersion(String certificateVersion) {
        this.certificateVersion = certificateVersion;
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

    //DODAO SAM GETERE I SETERE
    /////
    public Set<Extension> getExtensions() {
        return extensions;
    }

    public void setExtensions(Set<Extension> extensions) {
        this.extensions = extensions;
    }

    public ValidityPeriod getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(ValidityPeriod validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    //REFAKTURISI
    public Set<Certificate> getCertificationPath() {
        return certificationPath;
    }

    public void setCertificationPath(Set<Certificate> certificationPath) {
        this.certificationPath = certificationPath;
    }
    /////////


    public Set<String> getIssuedCertificates() {
        return issuedCertificates;
    }

    public void setIssuedCertificates(Set<String> issuedCertificates) {
        this.issuedCertificates = issuedCertificates;
    }

    public void addIssuedCertificate(String serialNumber) {
        this.issuedCertificates.add(serialNumber);
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "id=" + id +
                ", certificateVersion='" + certificateVersion + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", isActive=" + isActive +
                ", isCA=" + isCA +
                ", certificateStatus=" + certificateStatus +
                ", revokeReason=" + revokeReason +
                ", extensions=" + extensions +
                ", validityPeriod=" + validityPeriod +
                '}';
    }
}
