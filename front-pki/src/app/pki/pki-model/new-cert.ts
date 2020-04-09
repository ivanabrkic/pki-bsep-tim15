import { X500NameCustom } from './x500name';

export class NewCertificate{

    id:number;
    
    serialNumber:string;

    x500nameCustom:X500NameCustom;

    isCA:boolean;

    parentSerialNumber:string;

    issuedTo:string;
    issuedBy:string;

    startDate:Date;
    endDate:Date;

    NewCertificate(){}

}