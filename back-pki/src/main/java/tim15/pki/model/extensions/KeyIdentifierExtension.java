package tim15.pki.model.extensions;

import tim15.pki.model.Certificate;
import tim15.pki.model.Extension;

import javax.persistence.*;

@Entity
@Table(name="key_identifier_extension")
@PrimaryKeyJoinColumn(name = "id")
public class KeyIdentifierExtension extends Extension {

    @OneToOne
    @JoinColumn(name="serial_number", referencedColumnName = "serial_number", unique = true)
    private Certificate certificate;

    public KeyIdentifierExtension() {
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }
}
