package com.example.employeemanagementsystem.database;

import com.example.employeemanagementsystem.model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeDatabaseTest {

    private EmployeeDatabase<String> employeeDatabase;

    @BeforeEach
    void setUp() {
        employeeDatabase = new EmployeeDatabase<>();
    }

    @AfterEach
    void tearDown() {
        employeeDatabase = null;
    }

    @Test
    void addEmployee() {
        Employee<String> employee = new Employee<>("1", "John Doe", "IT", 50000, 4.5, 5, true);
        assertTrue(employeeDatabase.addEmployee(employee));
        assertFalse(employeeDatabase.addEmployee(employee)); // Duplicate ID
    }

    @Test
    void removeEmployee() {
        Employee<String> employee = new Employee<>("1", "John Doe", "IT", 50000, 4.5, 5, true);
        employeeDatabase.addEmployee(employee);
        assertTrue(employeeDatabase.removeEmployee("1"));
        assertFalse(employeeDatabase.removeEmployee("1")); // Already removed
    }

    @Test
    void updateEmployeeDetails() {
        Employee<String> employee = new Employee<>("1", "John Doe", "IT", 50000, 4.5, 5, true);
        employeeDatabase.addEmployee(employee);
        assertTrue(employeeDatabase.updateEmployeeDetails("1", "name", "Jane Doe"));
        assertEquals("Jane Doe", employeeDatabase.getEmployee("1").getName());
        assertFalse(employeeDatabase.updateEmployeeDetails("2", "name", "Jane Doe")); // Non-existent ID
    }

    @Test
    void getAllEmployees() {
        Employee<String> employee1 = new Employee<>("1", "John Doe", "IT", 50000, 4.5, 5, true);
        Employee<String> employee2 = new Employee<>("2", "Jane Smith", "HR", 60000, 4.0, 7, true);
        employeeDatabase.addEmployee(employee1);
        employeeDatabase.addEmployee(employee2);
        List<Employee<String>> employees = employeeDatabase.getAllEmployees();
        assertEquals(2, employees.size());
    }

    @Test
    void searchByDepartment() {
        Employee<String> employee1 = new Employee<>("1", "John Doe", "IT", 50000, 4.5, 5, true);
        Employee<String> employee2 = new Employee<>("2", "Jane Smith", "HR", 60000, 4.0, 7, true);
        employeeDatabase.addEmployee(employee1);
        employeeDatabase.addEmployee(employee2);
        List<Employee<String>> itEmployees = employeeDatabase.searchByDepartment("IT");
        assertEquals(1, itEmployees.size());
        assertEquals("John Doe", itEmployees.getFirst().getName());
    }

    @Test
    void searchByName() {
        Employee<String> employee1 = new Employee<>("1", "John Doe", "IT", 50000, 4.5, 5, true);
        Employee<String> employee2 = new Employee<>("2", "Jane Smith", "HR", 60000, 4.0, 7, true);
        employeeDatabase.addEmployee(employee1);
        employeeDatabase.addEmployee(employee2);
        List<Employee<String>> employees = employeeDatabase.searchByName("Jane");
        assertEquals(1, employees.size());
        assertEquals("Jane Smith", employees.getFirst().getName());
    }

    @Test
    void filterByMinimumRating() {
        Employee<String> employee1 = new Employee<>("1", "John Doe", "IT", 50000, 4.5, 5, true);
        Employee<String> employee2 = new Employee<>("2", "Jane Smith", "HR", 60000, 3.0, 7, true);
        employeeDatabase.addEmployee(employee1);
        employeeDatabase.addEmployee(employee2);
        List<Employee<String>> employees = employeeDatabase.filterByMinimumRating(4.0);
        assertEquals(1, employees.size());
        assertEquals("John Doe", employees.getFirst().getName());
    }

    @Test
    void filterBySalaryRange() {
        Employee<String> employee1 = new Employee<>("1", "John Doe", "IT", 50000, 4.5, 5, true);
        Employee<String> employee2 = new Employee<>("2", "Jane Smith", "HR", 60000, 4.0, 7, true);
        employeeDatabase.addEmployee(employee1);
        employeeDatabase.addEmployee(employee2);
        List<Employee<String>> employees = employeeDatabase.filterBySalaryRange(45000, 55000);
        assertEquals(1, employees.size());
        assertEquals("John Doe", employees.getFirst().getName());
    }

    @Test
    void filterEmployees() {
        Employee<String> employee1 = new Employee<>("1", "John Doe", "IT", 50000, 4.5, 5, true);
        Employee<String> employee2 = new Employee<>("2", "Jane Smith", "HR", 60000, 4.0, 7, true);
        employeeDatabase.addEmployee(employee1);
        employeeDatabase.addEmployee(employee2);
        List<Employee<String>> employees = employeeDatabase.filterEmployees(emp -> emp.getYearsOfExperience() > 5);
        assertEquals(1, employees.size());
        assertEquals("Jane Smith", employees.getFirst().getName());
    }

    @Test
    void getEmployeeIterator() {
        Employee<String> employee1 = new Employee<>("1", "John Doe", "IT", 50000, 4.5, 5, true);
        employeeDatabase.addEmployee(employee1);
        assertTrue(employeeDatabase.getEmployeeIterator().hasNext());
    }

    @Test
    void sortBySalary() {
        Employee<String> employee1 = new Employee<>("1", "John Doe", "IT", 50000, 4.5, 5, true);
        Employee<String> employee2 = new Employee<>("2", "Jane Smith", "HR", 60000, 4.0, 7, true);
        employeeDatabase.addEmployee(employee1);
        employeeDatabase.addEmployee(employee2);
        List<Employee<String>> employees = employeeDatabase.sortBySalary();
        assertEquals("Jane Smith", employees.getFirst().getName());
    }

    @Test
    void sortByPerformance() {
        Employee<String> employee1 = new Employee<>("1", "John Doe", "IT", 50000, 4.5, 5, true);
        Employee<String> employee2 = new Employee<>("2", "Jane Smith", "HR", 60000, 4.0, 7, true);
        employeeDatabase.addEmployee(employee1);
        employeeDatabase.addEmployee(employee2);
        List<Employee<String>> employees = employeeDatabase.sortByPerformance();
        assertEquals("John Doe", employees.getFirst().getName());
    }

    @Test
    void sortByExperience() {
        Employee<String> employee1 = new Employee<>("1", "John Doe", "IT", 50000, 4.5, 5, true);
        Employee<String> employee2 = new Employee<>("2", "Jane Smith", "HR", 60000, 4.0, 7, true);
        employeeDatabase.addEmployee(employee1);
        employeeDatabase.addEmployee(employee2);
        List<Employee<String>> employees = employeeDatabase.sortByExperience();
        assertEquals("Jane Smith", employees.getFirst().getName());
    }

    @Test
    void giveSalaryRaise() {
        Employee<String> employee1 = new Employee<>("1", "John Doe", "IT", 50000, 4.5, 5, true);
        employeeDatabase.addEmployee(employee1);
        int count = employeeDatabase.giveSalaryRaise(4.0, 10);
        assertEquals(1, count);
        assertEquals(55000.0, employeeDatabase.getEmployee("1").getSalary(),0.0001);
    }

    @Test
    void getTopPaidEmployees() {
        Employee<String> employee1 = new Employee<>("1", "John Doe", "IT", 50000, 4.5, 5, true);
        Employee<String> employee2 = new Employee<>("2", "Jane Smith", "HR", 60000, 4.0, 7, true);
        employeeDatabase.addEmployee(employee1);
        employeeDatabase.addEmployee(employee2);
        List<Employee<String>> employees = employeeDatabase.getTopPaidEmployees(1);
        assertEquals(1, employees.size());
        assertEquals("Jane Smith", employees.getFirst().getName());
    }

    @Test
    void getAverageSalaryByDepartment() {
        Employee<String> employee1 = new Employee<>("1", "John Doe", "IT", 50000, 4.5, 5, true);
        Employee<String> employee2 = new Employee<>("2", "Jane Smith", "IT", 60000, 4.0, 7, true);
        employeeDatabase.addEmployee(employee1);
        employeeDatabase.addEmployee(employee2);
        double averageSalary = employeeDatabase.getAverageSalaryByDepartment("IT");
        assertEquals(55000, averageSalary);
    }

    @Test
    void getEmployee() {
        Employee<String> employee = new Employee<>("1", "John Doe", "IT", 50000, 4.5, 5, true);
        employeeDatabase.addEmployee(employee);
        assertNotNull(employeeDatabase.getEmployee("1"));
        assertNull(employeeDatabase.getEmployee("2"));
    }

    @Test
    void getEmployeeCount() {
        Employee<String> employee1 = new Employee<>("1", "John Doe", "IT", 50000, 4.5, 5, true);
        Employee<String> employee2 = new Employee<>("2", "Jane Smith", "HR", 60000, 4.0, 7, true);
        employeeDatabase.addEmployee(employee1);
        employeeDatabase.addEmployee(employee2);
        assertEquals(2, employeeDatabase.getEmployeeCount());
    }
}