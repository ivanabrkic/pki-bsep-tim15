package tim15.pki.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Extension implements Serializable {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name="oid_num", nullable = false, unique = true)
    private String oid;

    @Column(name="name", nullable = false, unique = true)
    private String name;

    @Column(name="value", nullable = false)
    private String value;

    public Extension() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Extension{" +
                "id=" + id +
                ", oid='" + oid + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
