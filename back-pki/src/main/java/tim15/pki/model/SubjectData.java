package tim15.pki.model;

import org.bouncycastle.asn1.x500.X500Name;

import java.io.Serializable;
import java.security.PublicKey;

public class SubjectData implements Serializable {

    private PublicKey publicKey;

    private X500Name name;

    public SubjectData() {
    }

    public SubjectData(PublicKey publicKey, X500Name name){
        this.publicKey = publicKey;
        this.name = name;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public X500Name getName() {
        return name;
    }

    public void setName(X500Name name) {
        this.name = name;
    }

}
