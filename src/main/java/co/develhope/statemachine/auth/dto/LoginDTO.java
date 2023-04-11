package co.develhope.statemachine.auth.dto;

public class LoginDTO {

    private String email;
    private String password;

    public LoginDTO() { }

    public LoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}