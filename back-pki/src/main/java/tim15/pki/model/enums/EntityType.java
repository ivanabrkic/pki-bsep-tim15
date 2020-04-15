package tim15.pki.model.enums;

public enum EntityType {
    SERVICE,
    SUBSYSTEM,
    USER;

    public static EntityType toEnum(String type) {
        switch (type.toUpperCase()) {
            case "SERVICE":
                return SERVICE;
            case "SUBSYSTEM":
                return SUBSYSTEM;
            case "USER":
                return USER;
            default:
                throw new IllegalArgumentException("Status " + type + " does not exist.");
        }
    }

}
