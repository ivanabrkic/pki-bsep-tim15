package tim15.pki.dto;

import tim15.pki.dto.builders.CertificateGenDTOBuilder;

import java.util.Date;
import java.util.Set;

public class CertificateGenDTO {

    private Long id;

    private String serialNumber;

    private boolean isCA;

    private String parentSerialNumber;

    private String issuedTo;
    private String issuedBy;

    private Date startDate;
    private Date endDate;

    private Set<ExtensionDTO> extensions;

    private X500NameCustom x500NameCustom;

    public CertificateGenDTO() {
    }

    public CertificateGenDTO(Long id, String serialNumber, boolean isCA, String parentSerialNumber, String issuedTo, String issuedBy, Date startDate, Date endDate, Set<ExtensionDTO> extensions, X500NameCustom x500NameCustom) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.isCA = isCA;
        this.parentSerialNumber = parentSerialNumber;
        this.issuedTo = issuedTo;
        this.issuedBy = issuedBy;
        this.startDate = startDate;
        this.endDate = endDate;
        this.extensions = extensions;
        this.x500NameCustom = x500NameCustom;
    }

    public static CertificateGenDTOBuilder builder(){
        return new CertificateGenDTOBuilder();
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

    public boolean isCA() {
        return isCA;
    }

    public void setCA(boolean CA) {
        isCA = CA;
    }

    public String getParentSerialNumber() {
        return parentSerialNumber;
    }

    public void setParentSerialNumber(String parentSerialNumber) {
        this.parentSerialNumber = parentSerialNumber;
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

    public Set<ExtensionDTO> getExtensions() {
        return extensions;
    }

    public void setExtensions(Set<ExtensionDTO> extensions) {
        this.extensions = extensions;
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
                ", issuedTo='" + issuedTo + '\'' +
                ", issuedBy='" + issuedBy + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", extensions=" + extensions +
                ", x500NameCustom=" + x500NameCustom +
                '}';
    }
}
