export class X500NameCustom{

    commonName:string;
    organization:string;
    organizationalUnit:string;
    localityCity:string;
    stateProvince:string;
    countryCode:string; 

    streetAddress:string;
    postalCode:string;
    businessCategory:string;
    uid:string;
    serialNumber:string;
    title:string;
    name:string;
    surname:string;
    givenName:string;
    initials:string;
    dateOfBirth:Date;
    placeOfBirth:string;
    gender:string;
    countryOfCitizenship:string;
    countryOfResidence:string;
    email:string;
    telephoneNumber:string;
    generation:string;
    
    //BouncyCastle x500Name dont have these attrs
    // businessRegistrationNumber:string; 
    // jurisdictionState:string;
    // jurisdictionLocality:string;
    
    X500NameCustom(){}

}