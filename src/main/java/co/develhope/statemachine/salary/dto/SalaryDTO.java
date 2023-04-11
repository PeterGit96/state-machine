package co.develhope.statemachine.salary.dto;

import co.develhope.statemachine.user.dto.UserDTO;

public class SalaryDTO {

    private Long id;
    private Double amount;
    private Integer workingHours;
    private UserDTO user;

    public SalaryDTO() { }

    public SalaryDTO(Long id, Double amount, Integer workingHours, UserDTO user) {
        this.id = id;
        this.amount = amount;
        this.workingHours = workingHours;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Integer workingHours) {
        this.workingHours = workingHours;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

}
