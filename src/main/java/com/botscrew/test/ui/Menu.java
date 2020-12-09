package com.botscrew.test.ui;

import com.botscrew.test.domain.Department;
import com.botscrew.test.domain.Lector;
import com.botscrew.test.exception.DepartmentDoesNotExistException;
import com.botscrew.test.exception.EntityNotFoundException;
import com.botscrew.test.service.DepartmentService;
import com.botscrew.test.service.LectorService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static java.lang.Math.round;
import static java.lang.System.lineSeparator;

@Component
public class Menu {

    private final Scanner scanner = new Scanner(System.in);
    private final DepartmentService departmentService;
    private final LectorService lectorService;

    public Menu(DepartmentService departmentService, LectorService lectorService) {
        this.departmentService = departmentService;
        this.lectorService = lectorService;
    }

    public void printMenu() {
        System.out.println("a. Get head of department" + lineSeparator() +
                "b. Get department statistic" + lineSeparator() +
                "c. Get average salary by department" + lineSeparator() +
                "d. Get number of employees in the department" + lineSeparator() +
                "e. Global search" + lineSeparator());
        System.out.print("CHOOSE ACTION: ");
        String action = scanner.next("[a-e]");
        switch (action) {
            case "a":
                getHeadOfDepartment();
                break;
            case "b": {
                getDepartmentStatistic();
                break;
            }
            case "c": {
                getAverageSalary();
                break;
            }
            case "d": {
                getNumberOfEmployees();
                break;
            }
            case "e": {
                globalSearch();
                break;
            }
        }
    }

    private void getHeadOfDepartment() {
        String nameOfDepartment = getName();
        try {
            Lector head = lectorService.findHeadByDepartmentName(nameOfDepartment);
            System.out.printf("Head of department is %s %s%n", head.getName(), head.getSurname());
        } catch (DepartmentDoesNotExistException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void getDepartmentStatistic() {
        String nameOfDepartment = getName();
        try {
            Map<String, Long> statistic = departmentService.findDegreeStatisticByDepartmentName(nameOfDepartment);
            if (statistic.isEmpty()) {
                System.out.println("There is no employees on faculty");
            } else {
                statistic.forEach((key, value) -> System.out.println(key + ":" + value));
            }
        } catch (EntityNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void getAverageSalary() {
        String nameOfDepartment = getName();
        try {
            System.out.println("Average salary on faculty is " + round(departmentService.findAverageSalaryByDepartmentName(nameOfDepartment) * 100.0) / 100.0);
        } catch (EntityNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void getNumberOfEmployees() {
        String nameOfDepartment = getName();
        try {
            System.out.println("Number of employees in the department is " + departmentService.findAllLectorsByDepartmentName(nameOfDepartment));
        } catch (EntityNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void globalSearch() {
        System.out.print("Enter template: ");
        String line = scanner.next();
        List<Lector> lectors = new ArrayList<>(lectorService.findAllByFullNameContains(line));
        List<Department> departments = new ArrayList<>(departmentService.findByNameContaining(line));
        lectors.forEach(lector -> System.out.println(lector.getName() + " " + lector.getSurname()));
        departments.forEach(department -> System.out.println(department.getName()));
        if(lectors.isEmpty() && departments.isEmpty()) {
            System.out.println("Nothing founded");
        }
    }

    private String getName() {
        System.out.print("Enter department name: ");
        return scanner.next();
    }
}
