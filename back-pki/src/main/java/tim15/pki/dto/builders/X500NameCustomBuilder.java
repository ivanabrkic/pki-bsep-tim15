package tim15.pki.dto.builders;

import tim15.pki.dto.X500NameCustom;

public class X500NameCustomBuilder {
    private String commonName;
    private String organization;
    private String organizationalUnit;
    private String localityCity;
    private String stateProvince;
    private String countryCode;
    private String streetAddress;
    private String postalCode;
    private String businessCategory;
    private String uid;
    private String serialNumber;
    private String title;
    private String name;
    private String surname;
    private String givenName;
    private String initials;
    private String dateOfBirth;
    private String placeOfBirth;
    private String gender;
    private String countryOfCitizenship;
    private String countryOfResidence;
    private String email;
    private String telephoneNumber;
    private String generation;

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

    public X500NameCustomBuilder setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public X500NameCustomBuilder setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
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

    public X500NameCustomBuilder setUID(String uid) {
        this.uid = uid;
        return this;
    }

    public X500NameCustomBuilder setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public X500NameCustomBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public X500NameCustomBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public X500NameCustomBuilder setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public X500NameCustomBuilder setGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    public X500NameCustomBuilder setInitials(String initials) {
        this.initials = initials;
        return this;
    }

    public X500NameCustomBuilder setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public X500NameCustomBuilder setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
        return this;
    }

    public X500NameCustomBuilder setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public X500NameCustomBuilder setCountryOfCitizenship(String countryOfCitizenship) {
        this.countryOfCitizenship = countryOfCitizenship;
        return this;
    }

    public X500NameCustomBuilder setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
        return this;
    }

    public X500NameCustomBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public X500NameCustomBuilder setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
        return this;
    }

    public X500NameCustomBuilder setGeneration(String generation) {
        this.generation = generation;
        return this;
    }

    public X500NameCustom createX500NameCustom() {
        return new X500NameCustom(commonName, organization, organizationalUnit, localityCity, stateProvince, countryCode, streetAddress, postalCode, businessCategory, uid, serialNumber, title, name, surname, givenName, initials, dateOfBirth, placeOfBirth, gender, countryOfCitizenship, countryOfResidence, email, telephoneNumber, generation);
    }
}
