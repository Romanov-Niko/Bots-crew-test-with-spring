package com.botscrew.test.repository;

import com.botscrew.test.domain.Lector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static com.botscrew.test.TestData.*;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class LectorRepositoryTest {

    @Autowired
    private LectorRepository lectorRepository;

    @Test
    void givenBiology_whenFindAllByDepartmentName_thenReturnedThirdLector() {
        List<Lector> actualLectors = lectorRepository.findAllByDepartmentName("Biology");

        assertEquals(singletonList(thirdLector), actualLectors);
    }

    @Test
    void givenBiology_findHeadByDepartmentName_thenReturnedThirdLector() {
        Lector actualLector = lectorRepository.findHeadByDepartmentName("Biology").orElse(null);

        assertEquals(thirdLector, actualLector);
    }

    @Test
    void givenRd_findByNameContaining_thenReturnedThirdLector() {
        List<Lector> actualLectors = lectorRepository.findByNameContaining("rd");

        assertEquals(singletonList(thirdLector), actualLectors);
    }

    @Test
    void givenTor_findBySurnameContaining_thenReturnedAllLectors() {
        List<Lector> expectedLectors = new ArrayList<>();
        expectedLectors.add(firstLector);
        expectedLectors.add(secondLector);
        expectedLectors.add(thirdLector);

        List<Lector> actualLectors = lectorRepository.findBySurnameContaining("tor");

        assertEquals(expectedLectors, actualLectors);
    }
}