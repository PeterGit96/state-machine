package co.develhope.statemachine.auth.dto;

import co.develhope.statemachine.user.dto.UserDTO;

public class LoginRTO {

    private UserDTO user;
    private String JWT;

    public LoginRTO() { }

    public LoginRTO(UserDTO user, String JWT) {
        this.user = user;
        this.JWT = JWT;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getJWT() {
        return JWT;
    }

    public void setJWT(String JWT) {
        this.JWT = JWT;
    }

}
