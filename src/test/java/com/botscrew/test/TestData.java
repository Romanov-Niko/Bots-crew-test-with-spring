package com.botscrew.test;

import com.botscrew.test.domain.Department;
import com.botscrew.test.domain.Lector;

import static java.util.Collections.singletonList;

public class TestData {

    public static Lector firstLector = new Lector(1, "First", "Lector", "assistant", 1000);
    public static Lector secondLector = new Lector(2, "Second", "Lector", "associate professor", 2000);
    public static Lector thirdLector = new Lector(3, "Third", "Lector", "professor", 3000);

    public static Department appliedMathDepartment = new Department(1, "Applied math", firstLector, singletonList(firstLector));
    public static Department physicsDepartment = new Department(2, "Physics", secondLector, singletonList(secondLector));
    public static Department biologyDepartment = new Department(3, "Biology", thirdLector, singletonList(thirdLector));
}
