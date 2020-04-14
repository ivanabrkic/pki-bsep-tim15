package tim15.pki.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import tim15.pki.model.builders.CertificateBuilder;
import tim15.pki.model.enums.CertificateStatus;
import tim15.pki.model.enums.EntityType;
import tim15.pki.model.enums.RevokeReason;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Certificate implements Serializable {

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

    @JsonIgnore
    @ManyToMany
    @JoinColumn(name = "certificate_parent")
    private Set<Certificate> certificateParents;

    @JsonIgnore
    @ManyToMany
    @JoinColumn(name = "certificate_child", referencedColumnName = "certificate_parent")
    private Set<Certificate> certificateChildren;

    @JsonIgnore
    private transient ValidityPeriod validityPeriod;

    @Column(name = "issued_to", nullable = false)
    private String issuedTo;

    @Column(name = "issued_by", nullable = false)
    private String issuedBy;

    @Column(name = "entity_type", nullable = false)
    private EntityType entityType;

    public Certificate() {
    }

    public Certificate(Long id, String serialNumber, boolean isActive, boolean isCA, CertificateStatus certificateStatus, RevokeReason revokeReason, String issuedTo, String issuedBy, EntityType entityType) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.isActive = isActive;
        this.isCA = isCA;
        this.certificateStatus = certificateStatus;
        this.revokeReason = revokeReason;
        this.issuedTo = issuedTo;
        this.issuedBy = issuedBy;
        this.entityType = entityType;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public static CertificateBuilder builder(){
        return new CertificateBuilder();
    }

    public Set<Certificate> getCertificateParents() {
        return certificateParents;
    }

    public void setCertificateParents(Set<Certificate> certificateParents) {
        this.certificateParents = certificateParents;
    }

    public Set<Certificate> getCertificateChildren() {
        return certificateChildren;
    }

    public void setCertificateChildren(Set<Certificate> certificateChildren) {
        this.certificateChildren = certificateChildren;
    }

    public ValidityPeriod getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(ValidityPeriod validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public String getIssuedTo() {
        return issuedTo;
    }

    public void setIssuedTo(String issuedTo) {
        this.issuedTo = issuedTo;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
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

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public boolean getIsCA() {
        return isCA;
    }

    public void setIsCA(boolean CA) {
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
                ", certificateParents=" + certificateParents +
                ", certificateChildren=" + certificateChildren +
                ", validityPeriod=" + validityPeriod +
                ", issuedTo='" + issuedTo + '\'' +
                ", issuedBy='" + issuedBy + '\'' +
                ", entityType=" + entityType +
                '}';
    }
}
