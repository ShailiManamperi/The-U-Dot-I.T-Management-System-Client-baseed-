package lk.system.it.Dto;

public class UserDTO {
    private String username;
    private String dis_name;
    private String password;
    private String verification;
    private String hint;

    public UserDTO(String username, String dis_name, String password, String verification, String hint) {
        this.username = username;
        this.dis_name = dis_name;
        this.password = password;
        this.verification = verification;
        this.hint = hint;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDis_name() {
        return dis_name;
    }

    public void setDis_name(String dis_name) {
        this.dis_name = dis_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
