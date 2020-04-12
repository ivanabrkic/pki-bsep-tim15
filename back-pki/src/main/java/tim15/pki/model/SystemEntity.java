package tim15.pki.model;

import javax.persistence.*;

@Entity
public class SystemEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name="uid", unique = true, nullable = false)
    private String uid;

    public SystemEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "SystemEntity{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                '}';
    }
}
