package tim15.pki.model.enums;

public enum CertificateStatus {
    VALID,
    REVOKED,
    EXPIRED,
    UNKNOWN;

    public static CertificateStatus toEnum(String status) {
        switch (status.toUpperCase()) {
            case "VALID":
                return VALID;
            case "REVOKED":
                return REVOKED;
            case "EXPIRED":
                return EXPIRED;
            case "UNKNOWN":
                return UNKNOWN;
            default:
                throw new IllegalArgumentException("Status " + status + " does not exist.");
        }
    }
}
