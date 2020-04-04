package tim15.pki.model;

// Table or not?

public class PublicKeyInfo {

    private String algorithmId;

    private String publicKey;

    public PublicKeyInfo() {
    }

    public PublicKeyInfo(String algorithmId, String publicKey) {
        this.algorithmId = algorithmId;
        this.publicKey = publicKey;
    }

    public String getAlgorithmId() {
        return algorithmId;
    }

    public void setAlgorithmId(String algorithmId) {
        this.algorithmId = algorithmId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public String toString() {
        return "PublicKeyInfo{" +
                "algorithmId='" + algorithmId + '\'' +
                ", publicKey='" + publicKey + '\'' +
                '}';
    }
}
