package tim15.pki.dto.builders;

import tim15.pki.dto.CertificateGenDTO;
import tim15.pki.dto.X500NameCustom;

import java.util.Date;

public class CertificateGenDTOBuilder {
    private Long id;
    private String serialNumber;
    private boolean isCA;
    private String parentSerialNumber;
    private Date startDate;
    private Date endDate;
    private X500NameCustom x500NameCustom;
    private String entityType;

    public CertificateGenDTOBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public CertificateGenDTOBuilder setEntityType(String entityType) {
        this.entityType = entityType;
        return this;
    }

    public CertificateGenDTOBuilder setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public CertificateGenDTOBuilder setIsCA(boolean isCA) {
        this.isCA = isCA;
        return this;
    }

    public CertificateGenDTOBuilder setParentSerialNumber(String parentSerialNumber) {
        this.parentSerialNumber = parentSerialNumber;
        return this;
    }

    public CertificateGenDTOBuilder setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public CertificateGenDTOBuilder setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public CertificateGenDTOBuilder setX500NameCustom(X500NameCustom x500NameCustom) {
        this.x500NameCustom = x500NameCustom;
        return this;
    }

    public CertificateGenDTO createCertificateGenDTO() {
        return new CertificateGenDTO(id, serialNumber, isCA, parentSerialNumber, startDate, endDate, x500NameCustom, entityType);
    }
}
