package tim15.pki.dto;

import tim15.pki.model.IssuerData;
import tim15.pki.model.SubjectData;
import tim15.pki.model.ValidityPeriod;

public class CertificateViewDTO {

    private String subjectName;

    private String issuerName;

    private ValidityPeriod validityPeriod;

    private String serialNumber;

    public CertificateViewDTO() {
    }

    public CertificateViewDTO(String subjectName, String issuerName, ValidityPeriod validityPeriod, String serialNumber) {
        this.subjectName = subjectName;
        this.issuerName = issuerName;
        this.validityPeriod = validityPeriod;
        this.serialNumber = serialNumber;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public ValidityPeriod getValidityPeriod() {
        return validityPeriod;
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

    public void setValidityPeriod(ValidityPeriod validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
