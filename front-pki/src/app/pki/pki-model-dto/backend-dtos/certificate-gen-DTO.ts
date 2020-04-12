import { X500NameCustom } from './x500name-custom';
import { ExtensionDTO } from './extension-DTO';

export class CertificateGenDTO{

    id:number;
    serialNumber:String;
    isCA:boolean;
    parentSerialNumber:String;
    issuedTo:String;
    issuedBy:String;
    startDate:String;
    endDate:String;
    extensions:ExtensionDTO[];
    x500NameCustom:X500NameCustom;

    CertificateGenDTO(){}

}