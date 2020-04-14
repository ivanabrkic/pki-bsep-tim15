package tim15.pki.model.enums;

public enum RevokeReason {
    NOT_REVOKED,
    EXPIRED,
    KEY_COMPROMISE,
    CA_COMPROMISE,
    AFFILIATION_CHANGED,
    SUPERSEDED,
    CESSATION_OF_OPERATION,
    CERTIFICATE_HOLD,
    PRIVILEGE_WITHDRAWN,
    AA_COMPROMISE,
    UNKNOWN;


    public static RevokeReason toEnum(String status) {
        switch (status.toUpperCase()) {
            case "NOT REVOKED":
                return NOT_REVOKED;
            case "EXPIRED":
                return EXPIRED;
            case "KEY COMPROMISE":
                return KEY_COMPROMISE;
            case "CA COMPROMISE":
                return CA_COMPROMISE;
            case "AFFILIATION CHANGED":
                return AFFILIATION_CHANGED;
            case "SUPERSEDED":
                return SUPERSEDED;
            case "CESSATION OF OPERATION":
                return CESSATION_OF_OPERATION;
            case "CERTIFICATE HOLD":
                return CERTIFICATE_HOLD;
            case "PRIVILEGE WITHDRAWN":
                return PRIVILEGE_WITHDRAWN;
            case "AA COMPROMISE":
                return AA_COMPROMISE;
            case "UNKNOWN":
                return UNKNOWN;
            default:
                throw new IllegalArgumentException("Status " + status + " does not exist.");
        }
    }
}
