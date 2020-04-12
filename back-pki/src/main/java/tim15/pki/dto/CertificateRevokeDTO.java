package tim15.pki.dto;

import tim15.pki.model.enums.RevokeReason;

public class CertificateRevokeDTO {
    private String serialNumber;
    private RevokeReason revokeReason;

    public CertificateRevokeDTO() {
    }

    public CertificateRevokeDTO(String serialNumber, RevokeReason revokeReason) {
        this.serialNumber = serialNumber;
        this.revokeReason = revokeReason;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public RevokeReason getRevokeReason() {
        return revokeReason;
    }

    public void setRevokeReason(RevokeReason revokeReason) {
        this.revokeReason = revokeReason;
    }

    @Override
    public String toString() {
        return "CertificateRevokeDTO{" +
                "serialNumber='" + serialNumber + '\'' +
                ", revokeReason=" + revokeReason +
                '}';
    }
}
