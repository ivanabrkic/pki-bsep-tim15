package tim15.pki.model;

import javax.persistence.*;

public class Signature {

    private String algorithmID;

    private String value;

    public Signature() {
    }

    public Signature(String algorithmID, String value) {
        this.algorithmID = algorithmID;
        this.value = value;
    }

    public String getAlgorithmID() {
        return algorithmID;
    }

    public void setAlgorithmID(String algorithmID) {
        this.algorithmID = algorithmID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Signature{" +
                "algorithmID='" + algorithmID + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
