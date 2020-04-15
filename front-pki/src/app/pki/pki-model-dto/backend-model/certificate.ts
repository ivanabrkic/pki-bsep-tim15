import { ValidityPeriod } from './validity-period';

// export enum EntityType{
//     SERVICE,
//     SUBSYSTEM,
//     USER
// }

// export enum RevokeReason{
//     NOT_REVOKED,
//     EXPIRED,
//     KEY_COMPROMISE,
//     CA_COMPROMISE,
//     AFFILIATION_CHANGED,
//     SUPERSEDED,
//     CESSATION_OF_OPERATION,
//     CERTIFICATE_HOLD,
//     PRIVILEGE_WITHDRAWN,
//     AA_COMPROMISE,
//     UNKNOWN
// }

// export enum CertificateStatus{
//     VALID,
//     REVOKED,
//     EXPIRED,
//     UNKNOWN
// }

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
    entityType:String;

    Certificate(){}

}