package tim15.pki.dto;

public class CertificateDetailsDTO {

    private String serialNumber;

    private String validFrom;

    private String validTo;

    private String isCa;

    private String sigAlgName;

    private String issuerCN;
    private String issuerOrganization;
    private String issuerOrganizationalUnit;
    private String issuerLocalityCity;
    private String issuerStateProvince;
    private String issuerCountryCode;

    private String subjectCN;
    private String subjectOrganization;
    private String subjectOrganizationalUnit;
    private String subjectLocalityCity;
    private String subjectStateProvince;
    private String subjectCountryCode;

    private String subjectUID;
    private String subjectTitle;
    private String subjectName;
    private String subjectSurname;
    private String subjectGivenName;
    private String subjectInitials;
    private String subjectDateOfBirth;
    private String subjectPlaceOfBirth;
    private String subjectGender;
    private String subjectCountryOfCitizenship;
    private String subjectCountryOfResidence;
    private String subjectEmail;
    private String subjectTelephoneNumber;
    private String subjectStreet;
    private String subjectPostalCode;
    private String subjectBusinessCategory;
    private String subjectGeneration;

    public CertificateDetailsDTO() {
    }

    public CertificateDetailsDTO(String serialNumber, String validFrom, String validTo, String isCa, String subjectCN, String subjectOrganization, String subjectOrganizationalUnit, String subjectLocalityCity, String subjectStateProvince, String subjectCountryCode, String issuerCN, String issuerOrganization, String issuerOrganizationalUnit, String issuerLocalityCity, String issuerStateProvince, String issuerCountryCode) {
        this.serialNumber = serialNumber;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.isCa = isCa;
        this.subjectCN = subjectCN;
        this.subjectOrganization = subjectOrganization;
        this.subjectOrganizationalUnit = subjectOrganizationalUnit;
        this.subjectLocalityCity = subjectLocalityCity;
        this.subjectStateProvince = subjectStateProvince;
        this.subjectCountryCode = subjectCountryCode;
        this.issuerCN = issuerCN;
        this.issuerOrganization = issuerOrganization;
        this.issuerOrganizationalUnit = issuerOrganizationalUnit;
        this.issuerLocalityCity = issuerLocalityCity;
        this.issuerStateProvince = issuerStateProvince;
        this.issuerCountryCode = issuerCountryCode;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public String getValidTo() {
        return validTo;
    }

    public String getIsCa() {
        return isCa;
    }

    public String getSigAlgName() {
        return sigAlgName;
    }

    public String getSubjectCN() {
        return subjectCN;
    }

    public String getSubjectOrganization() {
        return subjectOrganization;
    }

    public String getSubjectOrganizationalUnit() {
        return subjectOrganizationalUnit;
    }

    public String getSubjectLocalityCity() {
        return subjectLocalityCity;
    }

    public String getSubjectStateProvince() {
        return subjectStateProvince;
    }

    public String getSubjectCountryCode() {
        return subjectCountryCode;
    }

    public String getIssuerCN() {
        return issuerCN;
    }

    public String getIssuerOrganization() {
        return issuerOrganization;
    }

    public String getIssuerOrganizationalUnit() {
        return issuerOrganizationalUnit;
    }

    public String getIssuerLocalityCity() {
        return issuerLocalityCity;
    }

    public String getIssuerStateProvince() {
        return issuerStateProvince;
    }

    public String getIssuerCountryCode() {
        return issuerCountryCode;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    public void setIsCa(String isCa) {
        this.isCa = isCa;
    }

    public void setSigAlgName(String sigAlgName) {
        this.sigAlgName = sigAlgName;
    }

    public void setSubjectCN(String subjectCN) {
        this.subjectCN = subjectCN;
    }

    public void setSubjectOrganization(String subjectOrganization) {
        this.subjectOrganization = subjectOrganization;
    }

    public void setSubjectOrganizationalUnit(String subjectOrganizationalUnit) {
        this.subjectOrganizationalUnit = subjectOrganizationalUnit;
    }

    public void setSubjectLocalityCity(String subjectLocalityCity) {
        this.subjectLocalityCity = subjectLocalityCity;
    }

    public void setSubjectStateProvince(String subjectStateProvince) {
        this.subjectStateProvince = subjectStateProvince;
    }

    public void setSubjectCountryCode(String subjectCountryCode) {
        this.subjectCountryCode = subjectCountryCode;
    }

    public void setIssuerCN(String issuerCN) {
        this.issuerCN = issuerCN;
    }

    public void setIssuerOrganization(String issuerOrganization) {
        this.issuerOrganization = issuerOrganization;
    }

    public void setIssuerOrganizationalUnit(String issuerOrganizationalUnit) {
        this.issuerOrganizationalUnit = issuerOrganizationalUnit;
    }

    public void setIssuerLocalityCity(String issuerLocalityCity) {
        this.issuerLocalityCity = issuerLocalityCity;
    }

    public void setIssuerStateProvince(String issuerStateProvince) {
        this.issuerStateProvince = issuerStateProvince;
    }

    public void setIssuerCountryCode(String issuerCountryCode) {
        this.issuerCountryCode = issuerCountryCode;
    }

    public String getSubjectUID() {
        return subjectUID;
    }

    public String getSubjectTitle() {
        return subjectTitle;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getSubjectSurname() {
        return subjectSurname;
    }

    public String getSubjectGivenName() {
        return subjectGivenName;
    }

    public String getSubjectInitials() {
        return subjectInitials;
    }

    public String getSubjectDateOfBirth() {
        return subjectDateOfBirth;
    }

    public String getSubjectPlaceOfBirth() {
        return subjectPlaceOfBirth;
    }

    public String getSubjectGender() {
        return subjectGender;
    }

    public String getSubjectCountryOfCitizenship() {
        return subjectCountryOfCitizenship;
    }

    public String getSubjectCountryOfResidence() {
        return subjectCountryOfResidence;
    }

    public String getSubjectEmail() {
        return subjectEmail;
    }

    public String getSubjectTelephoneNumber() {
        return subjectTelephoneNumber;
    }

    public String getSubjectStreet() {
        return subjectStreet;
    }

    public String getSubjectPostalCode() {
        return subjectPostalCode;
    }

    public String getSubjectBusinessCategory() {
        return subjectBusinessCategory;
    }

    public String getSubjectGeneration() {
        return subjectGeneration;
    }

    public void setSubjectUID(String subjectUID) {
        this.subjectUID = subjectUID;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setSubjectSurname(String subjectSurname) {
        this.subjectSurname = subjectSurname;
    }

    public void setSubjectGivenName(String subjectGivenName) {
        this.subjectGivenName = subjectGivenName;
    }

    public void setSubjectInitials(String subjectInitials) {
        this.subjectInitials = subjectInitials;
    }

    public void setSubjectDateOfBirth(String subjectDateOfBirth) {
        this.subjectDateOfBirth = subjectDateOfBirth;
    }

    public void setSubjectPlaceOfBirth(String subjectPlaceOfBirth) {
        this.subjectPlaceOfBirth = subjectPlaceOfBirth;
    }

    public void setSubjectGender(String subjectGender) {
        this.subjectGender = subjectGender;
    }

    public void setSubjectCountryOfCitizenship(String subjectCountryOfCitizenship) {
        this.subjectCountryOfCitizenship = subjectCountryOfCitizenship;
    }

    public void setSubjectCountryOfResidence(String subjectCountryOfResidence) {
        this.subjectCountryOfResidence = subjectCountryOfResidence;
    }

    public void setSubjectEmail(String subjectEmail) {
        this.subjectEmail = subjectEmail;
    }

    public void setSubjectTelephoneNumber(String subjectTelephoneNumber) {
        this.subjectTelephoneNumber = subjectTelephoneNumber;
    }

    public void setSubjectStreet(String subjectStreet) {
        this.subjectStreet = subjectStreet;
    }

    public void setSubjectPostalCode(String subjectPostalCode) {
        this.subjectPostalCode = subjectPostalCode;
    }

    public void setSubjectBusinessCategory(String subjectBusinessCategory) {
        this.subjectBusinessCategory = subjectBusinessCategory;
    }

    public void setSubjectGeneration(String subjectGeneration) {
        this.subjectGeneration = subjectGeneration;
    }
}
