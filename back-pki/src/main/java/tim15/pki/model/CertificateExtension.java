package tim15.pki.model;

import javax.persistence.*;

@Entity
public class CertificateExtension {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "serial_number", referencedColumnName = "serial_number")
    private Certificate certificate;

    @OneToOne
    @JoinColumn(name = "extension_oid", referencedColumnName = "oid_num")
    private Extension extension;

    @Column(name="critical", nullable = false)
    private boolean isCritical;

    public CertificateExtension() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }
//
//    public Set<Extension> getExtensions() {
//        return extensions;
//    }
//
//    public void setExtensions(Set<Extension> extensions) {
//        this.extensions = extensions;
//    }

    public boolean isCritical() {
        return isCritical;
    }

    public void setCritical(boolean critical) {
        isCritical = critical;
    }

    @Override
    public String toString() {
        return "CertificateExtension{" +
                "certificate=" + certificate +
                ", isCritical=" + isCritical +
                '}';
    }
}
