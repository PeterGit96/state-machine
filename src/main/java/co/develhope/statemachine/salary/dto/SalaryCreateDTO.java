package co.develhope.statemachine.salary.dto;

public class SalaryCreateDTO {

    private final Long id;
    private final Double amount;
    private final Integer workingHours;
    private final Long userId;

    public SalaryCreateDTO(Long id, Double amount, Integer workingHours, Long userId) {
        this.id = id;
        this.amount = amount;
        this.workingHours = workingHours;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getWorkingHours() {
        return workingHours;
    }

    public Long getUserId() {
        return userId;
    }

}
