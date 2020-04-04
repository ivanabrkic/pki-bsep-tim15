package tim15.pki.model;

import sun.security.x509.X500Name;

public class SubjectData {

    private transient PublicKeyInfo publicKeyInfo;

    private transient X500Name name;

    public SubjectData() {
    }

    public SubjectData(PublicKeyInfo publicKeyInfo, X500Name name){
        this.publicKeyInfo = publicKeyInfo;
        this.name = name;
    }

    public PublicKeyInfo getPublicKeyInfo() {
        return publicKeyInfo;
    }

    public void setPublicKeyInfo(PublicKeyInfo publicKeyInfo) {
        this.publicKeyInfo = publicKeyInfo;
    }

    public X500Name getName() {
        return name;
    }

    public void setName(X500Name name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SubjectData{" +
                ", publicKeyInfo=" + publicKeyInfo +
                ", name=" + name +
                '}';
    }
}
