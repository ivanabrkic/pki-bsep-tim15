package tim15.pki.dto.builders;

import tim15.pki.dto.X500NameCustom;

public class X500NameCustomBuilder {
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

    public X500NameCustomBuilder setCommonName(String commonName) {
        this.commonName = commonName;
        return this;
    }

    public X500NameCustomBuilder setOrganization(String organization) {
        this.organization = organization;
        return this;
    }

    public X500NameCustomBuilder setOrganizationalUnit(String organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
        return this;
    }

    public X500NameCustomBuilder setLocalityCity(String localityCity) {
        this.localityCity = localityCity;
        return this;
    }

    public X500NameCustomBuilder setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
        return this;
    }

    public X500NameCustomBuilder setCountryName(String countryName) {
        this.countryName = countryName;
        return this;
    }

    public X500NameCustomBuilder setCompanyStreetAddress(String companyStreetAddress) {
        this.companyStreetAddress = companyStreetAddress;
        return this;
    }

    public X500NameCustomBuilder setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public X500NameCustomBuilder setBusinessCategory(String businessCategory) {
        this.businessCategory = businessCategory;
        return this;
    }

    public X500NameCustomBuilder setBusinessRegistrationNumber(String businessRegistrationNumber) {
        this.businessRegistrationNumber = businessRegistrationNumber;
        return this;
    }

    public X500NameCustomBuilder setJurisdictionState(String jurisdictionState) {
        this.jurisdictionState = jurisdictionState;
        return this;
    }

    public X500NameCustomBuilder setJurisdictionLocality(String jurisdictionLocality) {
        this.jurisdictionLocality = jurisdictionLocality;
        return this;
    }

    public X500NameCustom createX500NameCustom() {
        return new X500NameCustom(commonName, organization, organizationalUnit, localityCity, stateProvince, countryName, companyStreetAddress, postalCode, businessCategory, businessRegistrationNumber, jurisdictionState, jurisdictionLocality);
    }
}
