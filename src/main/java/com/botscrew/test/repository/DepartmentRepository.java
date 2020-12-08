package com.botscrew.test.repository;

import com.botscrew.test.domain.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Integer> {

    List<Department> findAll();

    Optional<Department> findByName(String departmentName);

    List<Department> findByNameContaining(String template);
}
