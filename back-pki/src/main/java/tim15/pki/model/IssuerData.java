package tim15.pki.model;

import sun.security.x509.X500Name;
import java.security.PrivateKey;

public class IssuerData {

    private transient X500Name name;

    private transient PrivateKey privateKey;

    public IssuerData() {
    }

    public IssuerData(X500Name name, PrivateKey privateKey) {
        this.name = name;
        this.privateKey = privateKey;
    }

    public X500Name getName() {
        return name;
    }

    public void setName(X500Name name) {
        this.name = name;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public String toString() {
        return "IssuerData{" +
                "name=" + name +
                ", privateKey=" + privateKey +
                '}';
    }
}
