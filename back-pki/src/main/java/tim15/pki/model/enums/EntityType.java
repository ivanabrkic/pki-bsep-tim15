package tim15.pki.model.enums;

public enum EntityType {
    SERVICE,
    ORGANIZATION,
    USER;

    public static EntityType toEnum(String type) {
        switch (type.toUpperCase()) {
            case "SERVICE":
                return SERVICE;
            case "ORGANIZATION":
                return ORGANIZATION;
            case "USER":
                return USER;
            default:
                throw new IllegalArgumentException("Status " + type + " does not exist.");
        }
    }

}
