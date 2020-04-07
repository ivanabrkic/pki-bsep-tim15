package tim15.pki.dto;

import tim15.pki.model.IssuerData;
import tim15.pki.model.SubjectData;
import tim15.pki.model.ValidityPeriod;

public class CertificateGenDTO {

    private SubjectData subjectData;

    private IssuerData issuerData;

    private ValidityPeriod validityPeriod;

    private boolean isCA;

    private String serialNumber;

    public CertificateGenDTO() {
    }

    public CertificateGenDTO(SubjectData subjectData, IssuerData issuerData, ValidityPeriod validityPeriod, boolean isCA, String serialNumber) {
        this.subjectData = subjectData;
        this.issuerData = issuerData;
        this.validityPeriod = validityPeriod;
        this.isCA = isCA;
        this.serialNumber = serialNumber;
    }

    public SubjectData getSubjectData() {
        return subjectData;
    }

    public void setSubjectData(SubjectData subjectData) {
        this.subjectData = subjectData;
    }

    public IssuerData getIssuerData() {
        return issuerData;
    }

    public void setIssuerData(IssuerData issuerData) {
        this.issuerData = issuerData;
    }

    public ValidityPeriod getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(ValidityPeriod validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public boolean isCA() {
        return isCA;
    }

    public void setCA(boolean CA) {
        isCA = CA;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public String toString() {
        return "CertificateGenDTO{" +
                "subjectData=" + subjectData +
                ", issuerData=" + issuerData +
                ", validityPeriod=" + validityPeriod +
                ", isCA=" + isCA +
                ", serialNumber='" + serialNumber + '\'' +
                '}';
    }
}
