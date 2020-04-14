import { X500NameCustom } from './x500name-custom';
import { ExtensionDTO } from './extension-DTO';

export class CertificateGenDTO{

    id:number;
    serialNumber:String;
    isCA:boolean;
    parentSerialNumber:String;
    startDate:Date;
    endDate:Date;
    extensions:ExtensionDTO[];
    x500NameCustom:X500NameCustom;
    entityType:String;

    CertificateGenDTO(){}

}