package co.develhope.statemachine.user.dto;

import co.develhope.statemachine.user.entities.Role;

import java.time.LocalDateTime;
import java.util.Set;

public class UserDTO {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private LocalDateTime jwtCreatedOn;
    private Set<Role> roles;
    private boolean recordStatus;

    public UserDTO() { }

    public UserDTO(Long id, String name, String surname, String email, LocalDateTime jwtCreatedOn, Set<Role> roles, boolean recordStatus) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.jwtCreatedOn = jwtCreatedOn;
        this.roles = roles;
        this.recordStatus = recordStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getJwtCreatedOn() {
        return jwtCreatedOn;
    }

    public void setJwtCreatedOn(LocalDateTime jwtCreatedOn) {
        this.jwtCreatedOn = jwtCreatedOn;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(boolean recordStatus) {
        this.recordStatus = recordStatus;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", jwtCreatedOn=" + jwtCreatedOn +
                ", roles=" + roles +
                ", recordStatus=" + recordStatus +
                '}';
    }
}
