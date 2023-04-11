package co.develhope.statemachine.salary.repositories;

import co.develhope.statemachine.salary.entities.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {

    Optional<Salary> findByUserId(Long id);

}
