export class CertificateDetailsDTO {
    serialNumber: string;
    validFrom: string;
    validTo: string;
    isCa: string;
    sigAlgName: string;

    issuerCN: string;
    issuerOrganization: string;
    issuerOrganizationalUnit: string;
    issuerLocalityCity: string;
    issuerStateProvince: string;
    issuerCountryCode: string;

    subjectCN: string;
    subjectOrganization: string;
    subjectOrganizationalUnit: string;
    subjectLocalityCity: string;
    subjectStateProvince: string;
    subjectCountryCode: string;

    subjectUID: string;
    subjectTitle: string;
    subjectName: string;
    subjectSurname: string;
    subjectGivenName: string;
    subjectInitials: string;
    subjectDateOfBirth: string;
    subjectPlaceOfBirth: string;
    subjectGender: string;
    subjectCountryOfCitizenship: string;
    subjectCountryOfResidence: string;
    subjectEmail: string;
    subjectTelephoneNumber: string;
    subjectStreet: string;
    subjectPostalCode: string;
    subjectBusinessCategory: string;
    subjectGeneration: string;
}