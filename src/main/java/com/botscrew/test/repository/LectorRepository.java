package com.botscrew.test.repository;

import com.botscrew.test.domain.Lector;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LectorRepository extends CrudRepository<Lector, Integer> {

    List<Lector> findAll();

    @Query(value = "SELECT * FROM lectors " +
            "LEFT JOIN departments_lectors ON lectors.id = departments_lectors.lector_id " +
            "LEFT JOIN departments ON departments_lectors.department_id = departments.id " +
            "WHERE departments.name = :departmentName", nativeQuery = true)
    List<Lector> findAllByDepartmentName(@Param("departmentName") String departmentName);

    @Query(value = "SELECT * FROM lectors " +
            "LEFT JOIN departments ON lectors.id = head_id " +
            "WHERE departments.name = :departmentName", nativeQuery = true)
    Optional<Lector> findHeadByDepartmentName(@Param("departmentName") String departmentName);

    List<Lector> findByNameContaining(String template);

    List<Lector> findBySurnameContaining(String template);
}