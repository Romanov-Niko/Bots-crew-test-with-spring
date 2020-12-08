package com.botscrew.test.repository;

import com.botscrew.test.domain.Department;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.botscrew.test.TestData.biologyDepartment;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void givenBiology_whenFindByName_thenReturnedBiologyDepartment() {
        Department actualDepartment = departmentRepository.findByName("Biology").orElse(null);

        assertEquals(biologyDepartment, actualDepartment);
    }

    @Test
    void givenOlo_whenFindByNameContaining_thenReturnedBiologyDepartment() {
        List<Department> actualDepartment = departmentRepository.findByNameContaining("olo");

        assertEquals(singletonList(biologyDepartment), actualDepartment);
    }
}