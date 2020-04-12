package tim15.pki.dto;

public class ExtensionDTO {

    private Long id;
    private String oid;
    private String name;
    private String value;
    private boolean isCritical;

    public ExtensionDTO() {
    }

    public ExtensionDTO(Long id, String oid, String name, String value, boolean isCritical) {
        this.id = id;
        this.oid = oid;
        this.name = name;
        this.value = value;
        this.isCritical = isCritical;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean getIsCritical() {
        return isCritical;
    }

    public void setIsCritical(boolean critical) {
        isCritical = critical;
    }

    @Override
    public String toString() {
        return "ExtensionDTO{" +
                "id=" + id +
                ", oid='" + oid + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", isCritical=" + isCritical +
                '}';
    }
}
