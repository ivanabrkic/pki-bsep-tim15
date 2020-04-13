package tim15.pki.dto;

import tim15.pki.model.IssuerData;
import tim15.pki.model.SubjectData;
import tim15.pki.model.ValidityPeriod;

import java.util.Date;

public class CertificateViewDTO {

    private String subjectName;

    private String issuerName;

    private String validFrom;

    private String validTo;

    private String serialNumber;

    public CertificateViewDTO() {
    }

    public CertificateViewDTO(String subjectName, String issuerName, String validFrom, String validTo, String serialNumber) {
        this.subjectName = subjectName;
        this.issuerName = issuerName;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.serialNumber = serialNumber;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public String getValidTo() {
        return validTo;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }
}
