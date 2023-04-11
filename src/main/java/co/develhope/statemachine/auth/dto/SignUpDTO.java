package co.develhope.statemachine.auth.dto;

import java.time.LocalDateTime;

public class SignUpDTO {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private LocalDateTime jwtCreatedOn;

    public SignUpDTO() { }

    public SignUpDTO(Long id, String name, String surname, String email, String password, LocalDateTime jwtCreatedOn) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.jwtCreatedOn = jwtCreatedOn;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getJwtCreatedOn() {
        return jwtCreatedOn;
    }

}
