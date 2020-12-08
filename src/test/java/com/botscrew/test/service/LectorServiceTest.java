package com.botscrew.test.service;

import com.botscrew.test.domain.Lector;
import com.botscrew.test.exception.DepartmentDoesNotExistException;
import com.botscrew.test.exception.EntityNotFoundException;
import com.botscrew.test.exception.SalaryIsNegativeException;
import com.botscrew.test.repository.LectorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LectorServiceTest {

    @Mock
    private LectorRepository lectorRepository;

    @InjectMocks
    private LectorService lectorService;

    @Test
    void givenIdOfTheFirstLector_whenFindById_thenCalledLectorRepositoryGetByIdReturnedLectorWithGivenId() {
        Optional<Lector> expectedLector = Optional.of(new Lector());
        when(lectorRepository.findById(1)).thenReturn(expectedLector);

        Optional<Lector> actualLector = lectorService.findById(1);

        verify(lectorRepository, times(1)).findById(1);
        assertEquals(expectedLector, actualLector);
    }

    @Test
    void givenNothing_whenFindAll_thenCalledLectorRepositoryGetAllAndReturnedAllLectors() {
        List<Lector> expectedLectors = singletonList(new Lector());
        when(lectorRepository.findAll()).thenReturn(expectedLectors);

        List<Lector> actualLectors = lectorService.findAll();

        verify(lectorRepository, times(1)).findAll();
        assertEquals(expectedLectors, actualLectors);
    }

    @Test
    void givenCorrectLector_whenSave_thenCalledLectorRepositorySave() {
        Lector expectedLector = new Lector(1, "some", "great", "lector", 1);

        lectorService.save(expectedLector);

        verify(lectorRepository, times(1)).save(expectedLector);
    }

    @Test
    void givenNegativeSalary_whenSave_thenSalaryIsNegativeExceptionThrown() {
        Throwable exception = assertThrows(SalaryIsNegativeException.class, () -> lectorService.update(new Lector(1, "","","",-20)));
        assertEquals("Salary can not be negative", exception.getMessage());
        verify(lectorRepository, never()).save(new Lector(1, "","","",-20));
    }

    @Test
    void givenCorrectLector_whenUpdate_thenCalledLectorRepositoryUpdate() {
        Lector expectedLector = new Lector(1, "some", "great", "lector", 1);
        when(lectorRepository.findById(1)).thenReturn(Optional.of(expectedLector));

        lectorService.update(expectedLector);

        verify(lectorRepository, times(1)).save(expectedLector);
    }

    @Test
    void givenNonExistentLectorId_whenUpdate_thenEntityNotFoundExceptionThrown() {
        Throwable exception = assertThrows(EntityNotFoundException.class, () -> lectorService.update(new Lector()));
        assertEquals("Lector with id 0 is not present", exception.getMessage());
        verify(lectorRepository, never()).save(new Lector());
    }

    @Test
    void givenId_whenDelete_thenCalledLectorRepositoryDelete() {
        lectorService.delete(1);

        verify(lectorRepository, times(1)).deleteById(1);
    }

    @Test
    void givenBiology_whenFindHeadByDepartmentName_thenCalledLectorRepositoryGetHeadOfDepartment() {
        Lector expectedLector = new Lector(1, "some", "great", "lector", 1);
        when(lectorRepository.findHeadByDepartmentName("Biology")).thenReturn(Optional.of(expectedLector));

        lectorService.findHeadByDepartmentName("Biology");

        verify(lectorRepository, times(1)).findHeadByDepartmentName("Biology");
    }

    @Test
    void givenNonExistentDepartment_whenFindHeadByDepartmentName_thenDepartmentDoesNotExistExceptionThrown() {
        Throwable exception = assertThrows(DepartmentDoesNotExistException.class, () -> lectorService.findHeadByDepartmentName("some"));
        assertEquals("Department with name some is not exist", exception.getMessage());
    }

    @Test
    void givenString_whenFindAllByFullNameContains_thenCalledLectorRepositorySearchIfContainsAndReturnedListOfLectors() {
        when(lectorRepository.findByNameContaining("some")).thenReturn(singletonList(new Lector(1, "some", "test", "lector", 1)));
        when(lectorRepository.findBySurnameContaining("some")).thenReturn(singletonList(new Lector(1, "some", "test", "lector", 1)));

        List<Lector> actualResult = lectorService.findAllByFullNameContains("some");

        verify(lectorRepository, times(1)).findByNameContaining("some");
        verify(lectorRepository, times(1)).findBySurnameContaining("some");
        assertEquals(singletonList(new Lector(1, "some", "test", "lector", 1)), actualResult);
    }
}