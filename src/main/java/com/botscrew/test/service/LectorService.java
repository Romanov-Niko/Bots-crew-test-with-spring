package com.botscrew.test.service;

import com.botscrew.test.domain.Lector;
import com.botscrew.test.exception.DepartmentDoesNotExistException;
import com.botscrew.test.exception.EntityNotFoundException;
import com.botscrew.test.exception.SalaryIsNegativeException;
import com.botscrew.test.repository.LectorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LectorService {

    private final LectorRepository lectorRepository;

    public LectorService(LectorRepository lectorRepository) {
        this.lectorRepository = lectorRepository;
    }

    public Optional<Lector> findById(int id) {
        return lectorRepository.findById(id);
    }

    public List<Lector> findAll() {
        return lectorRepository.findAll();
    }

    public void save(Lector lector) {
        verifySalaryIsNotNegative(lector.getSalary());
        lectorRepository.save(lector);
    }

    public void update(Lector lector) {
        verifySalaryIsNotNegative(lector.getSalary());
        verifyLectorPresent(lector.getId());
        lectorRepository.save(lector);
    }

    public void delete(int id) {
        lectorRepository.deleteById(id);
    }

    public Lector findHeadByDepartmentName(String departmentName) {
        return lectorRepository.findHeadByDepartmentName(departmentName).orElseThrow(() -> new DepartmentDoesNotExistException(
                String.format("Department with name %s is not exist", departmentName)
        ));
    }

    public List<Lector> findAllByFullNameContains(String template) {
        List<Lector> lectorsByName = lectorRepository.findByNameContaining(template);
        List<Lector> lectorsBySurname = lectorRepository.findBySurnameContaining(template);
        lectorsByName.forEach(lector -> {
            if (!lectorsBySurname.contains(lector)) {
                lectorsBySurname.add(lector);
            }
        });
        return lectorsBySurname;
    }

    public List<Lector> findAllByDepartmentName(String departmentName) {
        return lectorRepository.findAllByDepartmentName(departmentName);
    }

    private void verifySalaryIsNotNegative(int salary) {
        if (salary < 0) {
            throw new SalaryIsNegativeException("Salary can not be negative");
        }
    }

    private void verifyLectorPresent(int id) {
        lectorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Lector with id %d is not present", id)));
    }
}
