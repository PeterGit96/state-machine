package co.develhope.statemachine.salary.services;

import co.develhope.statemachine.salary.dto.SalaryCreateDTO;
import co.develhope.statemachine.salary.dto.SalaryDTO;
import co.develhope.statemachine.salary.entities.Salary;
import co.develhope.statemachine.salary.repositories.SalaryRepository;
import co.develhope.statemachine.user.services.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalaryService {

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private UserService userService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public SalaryDTO createSalary(@NotNull SalaryCreateDTO salaryDTO) {
        Salary salary = this.convertToEntity(salaryDTO);
        salary = salaryRepository.saveAndFlush(salary);
        entityManager.refresh(salary);
        return convertToDTO(salary);
    }

    public List<SalaryDTO> getAllSalaries() {
        return salaryRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SalaryDTO getSalaryByUserId(Long id) {
        Salary salary = salaryRepository.findByUserId(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return convertToDTO(salary);
    }

    public Salary convertToEntity(@NotNull SalaryCreateDTO salaryDTO) {
        return modelMapper.map(salaryDTO, Salary.class);
    }

    public Salary convertToEntity(@NotNull SalaryDTO salaryDTO) {
        return modelMapper.map(salaryDTO, Salary.class);
    }

    public SalaryDTO convertToDTO(@NotNull Salary salary) {
        return modelMapper.map(salary, SalaryDTO.class);
    }

}
