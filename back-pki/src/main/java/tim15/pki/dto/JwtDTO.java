package tim15.pki.dto;

public class JwtDTO {
    private String jwt;

    public JwtDTO() {
    }

    public JwtDTO(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}

