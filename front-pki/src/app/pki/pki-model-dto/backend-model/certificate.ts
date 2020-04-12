import { ValidityPeriod } from './validity-period';

export class Certificate{

    id:number;
    serialNumber:String;
    isActive:boolean;
    isCA:boolean;
    certificateStatus:String;
    revokeReason:String;
    certificateParents:Certificate[];
    certificateChildren:Certificate[];
    validityPeriod:ValidityPeriod;
    issuedTo:String;
    issuedBy:String;

    Certificate(){}

}