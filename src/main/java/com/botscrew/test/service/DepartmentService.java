package com.botscrew.test.service;

import com.botscrew.test.domain.Department;
import com.botscrew.test.domain.Lector;
import com.botscrew.test.exception.DepartmentNameNotUniqueException;
import com.botscrew.test.exception.EntityNotFoundException;
import com.botscrew.test.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Service
public class DepartmentService {

    private final LectorService lectorService;
    private final DepartmentRepository departmentRepository;

    public DepartmentService(LectorService lectorService, DepartmentRepository departmentRepository) {
        this.lectorService = lectorService;
        this.departmentRepository = departmentRepository;
    }

    public Optional<Department> findById(int id) {
        return departmentRepository.findById(id);
    }

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public void save(Department department) {
        verifyDepartmentUnique(department);
        verifyHeadPresent(department.getId());
        departmentRepository.save(department);
    }

    public void update(Department department) {
        verifyDepartmentUnique(department);
        verifyDepartmentPresentById(department.getId());
        verifyHeadPresent(department.getId());
        departmentRepository.save(department);
    }

    public void delete(int id) {
        departmentRepository.deleteById(id);
    }

    public int findAllLectorsByDepartmentName(String departmentName) {
        verifyDepartmentPresentByName(departmentName);
        return lectorService.findAllByDepartmentName(departmentName).size();
    }

    public double findAverageSalaryByDepartmentName(String departmentName) {
        verifyDepartmentPresentByName(departmentName);
        List<Lector> lectors = lectorService.findAllByDepartmentName(departmentName);
        return lectors.stream()
                .mapToInt(Lector::getSalary)
                .average()
                .orElse(Double.NaN);
    }

    public Map<String, Long> findDegreeStatisticByDepartmentName(String departmentName) {
        verifyDepartmentPresentByName(departmentName);
        List<Lector> lectors = lectorService.findAllByDepartmentName(departmentName);
        return lectors.stream()
                .collect(groupingBy(Lector::getDegree, counting()));
    }

    public List<Department> findByNameContaining(String template) {
        return departmentRepository.findByNameContaining(template);
    }

    private void verifyDepartmentPresentByName(String nameOfDepartment) {
        departmentRepository.findByName(nameOfDepartment).orElseThrow(() -> new EntityNotFoundException(
                String.format("Department with name %s is not present", nameOfDepartment)));
    }

    private void verifyDepartmentPresentById(int id) {
        departmentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Department with id %d is not present", id)));
    }

    private void verifyDepartmentUnique(Department department) {
        departmentRepository.findByName(department.getName()).ifPresent(departmentWithSameName -> {
            if (department.getId() != departmentWithSameName.getId()) {
                throw new DepartmentNameNotUniqueException(String.format("Department with name %s already exist", department.getName()));
            }
        });
    }

    private void verifyHeadPresent(int id) {
        lectorService.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Lector with id %d is not present", id)));
    }
}
