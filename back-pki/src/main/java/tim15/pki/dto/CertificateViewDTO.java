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

    private String status;

    private String revokeReason;

    public CertificateViewDTO() {
    }

    public CertificateViewDTO(String subjectName, String issuerName, String validFrom, String validTo, String serialNumber, String revokeReason) {
        this.subjectName = subjectName;
        this.issuerName = issuerName;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.serialNumber = serialNumber;
        this.revokeReason = revokeReason;
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

    public String getRevokeReason() {
        return revokeReason;
    }

    public void setRevokeReason(String revokeReason) {
        this.revokeReason = revokeReason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
