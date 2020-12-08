package com.botscrew.test.service;

import com.botscrew.test.domain.Department;
import com.botscrew.test.domain.Lector;
import com.botscrew.test.exception.EntityNotFoundException;
import com.botscrew.test.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.botscrew.test.TestData.biologyDepartment;
import static com.botscrew.test.TestData.thirdLector;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private LectorService lectorService;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    void givenIdOfTheFirstDepartment_whenFindById_thenCalledDepartmentRepositoryGetByIdReturnedDepartmentWithGivenId() {
        Optional<Department> expectedDepartment = Optional.of(new Department());
        when(departmentRepository.findById(1)).thenReturn(expectedDepartment);

        Optional<Department> actualDepartment = departmentService.findById(1);

        verify(departmentRepository, times(1)).findById(1);
        assertEquals(expectedDepartment, actualDepartment);
    }

    @Test
    void givenNothing_whenFindAll_thenCalledDepartmentRepositoryGetAllAndReturnedAllDepartments() {
        List<Department> expectedDepartments = singletonList(new Department());
        when(departmentRepository.findAll()).thenReturn(expectedDepartments);

        List<Department> actualDepartments = departmentService.findAll();

        verify(departmentRepository, times(1)).findAll();
        assertEquals(expectedDepartments, actualDepartments);
    }

    @Test
    void givenCorrectDepartment_whenSave_thenCalledDepartmentRepositorySave() {
        when(lectorService.findById(3)).thenReturn(Optional.of(new Lector()));

        departmentService.save(biologyDepartment);

        verify(departmentRepository, times(1)).save(biologyDepartment);
    }

    @Test
    void givenCorrectDepartment_whenUpdate_thenCalledDepartmentRepositoryUpdate() {
        when(lectorService.findById(3)).thenReturn(Optional.of(new Lector()));
        when(departmentRepository.findById(3)).thenReturn(Optional.of(new Department()));

        departmentService.update(biologyDepartment);

        verify(departmentRepository, times(1)).save(biologyDepartment);
    }

    @Test
    void givenNonExistentDepartmentId_whenUpdate_thenEntityNotFoundExceptionThrown() {
        Throwable exception = assertThrows(EntityNotFoundException.class, () -> departmentService.update(new Department()));
        assertEquals("Department with id 0 is not present", exception.getMessage());
        verify(departmentRepository, never()).save(new Department());
    }

    @Test
    void givenId_whenDelete_thenCalledDepartmentRepositoryDelete() {
        departmentService.delete(1);

        verify(departmentRepository, times(1)).deleteById(1);
    }

    @Test
    void givenBiology_whenFindAllLectorsByDepartmentName_thenCalledDepartmentRepositoryGetQuantityOfEmployeeByName() {
        when(departmentRepository.findByName("Biology")).thenReturn(Optional.of(biologyDepartment));
        when(lectorService.findAllByDepartmentName("Biology")).thenReturn(singletonList(thirdLector));

        int actualResult = departmentService.findAllLectorsByDepartmentName("Biology");

        verify(lectorService, times(1)).findAllByDepartmentName("Biology");
        assertEquals(1, actualResult);
    }

    @Test
    void givenBiology_whenFindAverageSalaryByDepartmentName_thenCalledDepartmentRepositoryGetAverageSalaryByName() {
        when(departmentRepository.findByName("Biology")).thenReturn(Optional.of(biologyDepartment));
        when(lectorService.findAllByDepartmentName("Biology")).thenReturn(singletonList(thirdLector));

        double actualResult = departmentService.findAverageSalaryByDepartmentName("Biology");

        verify(lectorService, times(1)).findAllByDepartmentName("Biology");
        assertEquals(3000.0, actualResult);
    }

    @Test
    void givenBiology_whenFindDegreeStatisticByDepartmentName_thenCalledDepartmentRepositoryGetDegreeStatisticByName() {
        Map<String, Long> expectedResult = new HashMap<>();
        expectedResult.put("professor", 1L);
        when(departmentRepository.findByName("Biology")).thenReturn(Optional.of(biologyDepartment));
        when(lectorService.findAllByDepartmentName("Biology")).thenReturn(singletonList(thirdLector));

        Map<String, Long> actualResult = departmentService.findDegreeStatisticByDepartmentName("Biology");

        verify(lectorService, times(1)).findAllByDepartmentName("Biology");
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void givenString_whenFindByNameContaining_thenCalledDepartmentRepositorySearchIfContainsAndReturnedListOfDepartments() {
        when(departmentRepository.findByNameContaining("some")).thenReturn(singletonList(biologyDepartment));

        List<Department> actualResult = departmentService.findByNameContaining("some");

        verify(departmentRepository, times(1)).findByNameContaining("some");
        assertEquals(singletonList(biologyDepartment), actualResult);
    }
}