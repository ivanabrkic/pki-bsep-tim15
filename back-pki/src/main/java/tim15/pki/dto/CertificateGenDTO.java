package tim15.pki.dto;

import tim15.pki.dto.builders.CertificateGenDTOBuilder;

import java.io.Serializable;
import java.util.Date;

public class CertificateGenDTO implements Serializable {

    private Long id;

    private String serialNumber;

    private boolean isCA;

    private String parentSerialNumber;

    private Date startDate;
    private Date endDate;

    private X500NameCustom x500NameCustom;

    private String entityType;

    public CertificateGenDTO() {
    }

    public CertificateGenDTO(Long id, String serialNumber, boolean isCA, String parentSerialNumber, Date startDate, Date endDate, X500NameCustom x500NameCustom, String entityType) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.isCA = isCA;
        this.parentSerialNumber = parentSerialNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.x500NameCustom = x500NameCustom;
        this.entityType = entityType;
    }

    public static CertificateGenDTOBuilder builder(){
        return new CertificateGenDTOBuilder();
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
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

    public boolean getIsCA() {
        return isCA;
    }

    public void setIsCA(boolean CA) {
        isCA = CA;
    }

    public String getParentSerialNumber() {
        return parentSerialNumber;
    }

    public void setParentSerialNumber(String parentSerialNumber) {
        this.parentSerialNumber = parentSerialNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public X500NameCustom getX500NameCustom() {
        return x500NameCustom;
    }

    public void setX500NameCustom(X500NameCustom x500NameCustom) {
        this.x500NameCustom = x500NameCustom;
    }

    @Override
    public String toString() {
        return "CertificateGenDTO{" +
                "id=" + id +
                ", serialNumber='" + serialNumber + '\'' +
                ", isCA=" + isCA +
                ", parentSerialNumber='" + parentSerialNumber + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", x500NameCustom=" + x500NameCustom +
                '}';
    }
}
