package tim15.pki.dto;

public class getCertificatesDTO {

    private String certType;
    private String password;

    public getCertificatesDTO(String certType, String password) {
        this.certType = certType;
        this.password = password;
    }

    public getCertificatesDTO() {
    }

    public String getCertType() {
        return certType;
    }

    public String getPassword() {
        return password;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
