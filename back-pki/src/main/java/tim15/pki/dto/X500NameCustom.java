package tim15.pki.dto;

import tim15.pki.dto.builders.X500NameCustomBuilder;

import java.io.Serializable;
import java.util.Date;

public class X500NameCustom implements Serializable {

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
    private Date dateOfBirth;
    private String placeOfBirth;
    private String gender;
    private String countryOfCitizenship;
    private String countryOfResidence;
    private String email;
    private String telephoneNumber;
    private String generation;

    public X500NameCustom() {
    }

    public X500NameCustom(String commonName, String organization, String organizationalUnit, String localityCity, String stateProvince, String countryCode, String streetAddress, String postalCode, String businessCategory, String uid, String serialNumber, String title, String name, String surname, String givenName, String initials, Date dateOfBirth, String placeOfBirth, String gender, String countryOfCitizenship, String countryOfResidence, String email, String telephoneNumber, String generation) {
        this.commonName = commonName;
        this.organization = organization;
        this.organizationalUnit = organizationalUnit;
        this.localityCity = localityCity;
        this.stateProvince = stateProvince;
        this.countryCode = countryCode;
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.businessCategory = businessCategory;
        this.uid = uid;
        this.serialNumber = serialNumber;
        this.title = title;
        this.name = name;
        this.surname = surname;
        this.givenName = givenName;
        this.initials = initials;
        this.dateOfBirth = dateOfBirth;
        this.placeOfBirth = placeOfBirth;
        this.gender = gender;
        this.countryOfCitizenship = countryOfCitizenship;
        this.countryOfResidence = countryOfResidence;
        this.email = email;
        this.telephoneNumber = telephoneNumber;
        this.generation = generation;
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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountryOfCitizenship() {
        return countryOfCitizenship;
    }

    public void setCountryOfCitizenship(String countryOfCitizenship) {
        this.countryOfCitizenship = countryOfCitizenship;
    }

    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    @Override
    public String toString() {
        return "X500NameCustom{" +
                "commonName='" + commonName + '\'' +
                ", organization='" + organization + '\'' +
                ", organizationalUnit='" + organizationalUnit + '\'' +
                ", localityCity='" + localityCity + '\'' +
                ", stateProvince='" + stateProvince + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", businessCategory='" + businessCategory + '\'' +
                ", UID='" + uid + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", givenName='" + givenName + '\'' +
                ", initials='" + initials + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", placeOfBirth='" + placeOfBirth + '\'' +
                ", gender='" + gender + '\'' +
                ", countryOfCitizenship='" + countryOfCitizenship + '\'' +
                ", countryOfResidence='" + countryOfResidence + '\'' +
                ", email='" + email + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", generation='" + generation + '\'' +
                '}';
    }
}
