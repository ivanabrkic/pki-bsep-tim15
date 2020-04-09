package tim15.pki.dto;

import tim15.pki.dto.builders.X500NameCustomBuilder;

public class X500NameCustom {

    private String commonName;
    private String organization;
    private String organizationalUnit;
    private String localityCity;
    private String stateProvince;
    private String countryName;
    private String companyStreetAddress;
    private String postalCode;
    private String businessCategory;
    private String businessRegistrationNumber;
    private String jurisdictionState;
    private String jurisdictionLocality;

    public X500NameCustom() {
    }

    public X500NameCustom(String commonName, String organization, String organizationalUnit, String localityCity, String stateProvince, String countryName, String companyStreetAddress, String postalCode, String businessCategory, String businessRegistrationNumber, String jurisdictionState, String jurisdictionLocality) {
        this.commonName = commonName;
        this.organization = organization;
        this.organizationalUnit = organizationalUnit;
        this.localityCity = localityCity;
        this.stateProvince = stateProvince;
        this.countryName = countryName;
        this.companyStreetAddress = companyStreetAddress;
        this.postalCode = postalCode;
        this.businessCategory = businessCategory;
        this.businessRegistrationNumber = businessRegistrationNumber;
        this.jurisdictionState = jurisdictionState;
        this.jurisdictionLocality = jurisdictionLocality;
    }

    public static X500NameCustomBuilder builder(){
        return new X500NameCustomBuilder();
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getOrganizationalUnit() {
        return organizationalUnit;
    }

    public void setOrganizationalUnit(String organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
    }

    public String getLocalityCity() {
        return localityCity;
    }

    public void setLocalityCity(String localityCity) {
        this.localityCity = localityCity;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCompanyStreetAddress() {
        return companyStreetAddress;
    }

    public void setCompanyStreetAddress(String companyStreetAddress) {
        this.companyStreetAddress = companyStreetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getBusinessCategory() {
        return businessCategory;
    }

    public void setBusinessCategory(String businessCategory) {
        this.businessCategory = businessCategory;
    }

    public String getBusinessRegistrationNumber() {
        return businessRegistrationNumber;
    }

    public void setBusinessRegistrationNumber(String businessRegistrationNumber) {
        this.businessRegistrationNumber = businessRegistrationNumber;
    }

    public String getJurisdictionState() {
        return jurisdictionState;
    }

    public void setJurisdictionState(String jurisdictionState) {
        this.jurisdictionState = jurisdictionState;
    }

    public String getJurisdictionLocality() {
        return jurisdictionLocality;
    }

    public void setJurisdictionLocality(String jurisdictionLocality) {
        this.jurisdictionLocality = jurisdictionLocality;
    }

    @Override
    public String toString() {
        return "X500NameCustom{" +
                "commonName='" + commonName + '\'' +
                ", organization='" + organization + '\'' +
                ", organizationalUnit='" + organizationalUnit + '\'' +
                ", localityCity='" + localityCity + '\'' +
                ", stateProvince='" + stateProvince + '\'' +
                ", countryName='" + countryName + '\'' +
                ", companyStreetAddress='" + companyStreetAddress + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", businessCategory='" + businessCategory + '\'' +
                ", businessRegistrationNumber='" + businessRegistrationNumber + '\'' +
                ", jurisdictionState='" + jurisdictionState + '\'' +
                ", jurisdictionLocality='" + jurisdictionLocality + '\'' +
                '}';
    }
}
