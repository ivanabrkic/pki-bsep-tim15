export class CertificateViewDTO {
    subjectName: string;
    issuerName: string;
    validFrom: Date
    validTo: Date;
    serialNumber: string;
    status: string;
    revokeReason: string;
}