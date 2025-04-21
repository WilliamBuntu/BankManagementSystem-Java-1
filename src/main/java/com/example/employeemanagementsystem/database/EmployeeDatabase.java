package com.example.employeemanagementsystem.database;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import com.example.employeemanagementsystem.model.Employee;
import com.example.employeemanagementsystem.utils.EmployeePerformanceComparator;
import com.example.employeemanagementsystem.utils.EmployeeSalaryComparator;

/**
 * EmployeeDatabase class that manages employee records using Collections.
 *
 * @param <T> Type of the employee ID
 */
public class EmployeeDatabase<T> {
    private final Map<T, Employee<T>> employees;

    public EmployeeDatabase() {
        employees = new HashMap<>();
    }

    /**
     * Adds a new employee to the database.
     *
     * @param employee The employee to add
     * @return true if the employee was added successfully, false if an employee with the same ID already exists
     */
    public boolean addEmployee(Employee<T> employee) {
        if (employees.containsKey(employee.getEmployeeId())) {
            return false;
        }
        employees.put(employee.getEmployeeId(), employee);
        return true;
    }

    /**
     * Removes an employee from the database.
     *
     * @param employeeId The ID of the employee to remove
     * @return true if the employee was removed successfully, false if no employee with the given ID exists
     */
    public boolean removeEmployee(T employeeId) {
        if (!employees.containsKey(employeeId)) {
            return false;
        }
        employees.remove(employeeId);
        return true;
    }

    /**
     * Updates an employee's details.
     *
     * @param employeeId The ID of the employee to update
     * @param field The field to update (name, department, salary, performanceRating, yearsOfExperience, isActive)
     * @param newValue The new value for the field
     * @return true if the update was successful, false otherwise
     */
    public boolean updateEmployeeDetails(T employeeId, String field, Object newValue) {
        if (!employees.containsKey(employeeId)) {
            return false;
        }

        Employee<T> employee = employees.get(employeeId);

        try {
            switch (field.toLowerCase()) {
                case "name":
                    employee.setName((String) newValue);
                    break;
                case "department":
                    employee.setDepartment((String) newValue);
                    break;
                case "salary":
                    employee.setSalary((Double) newValue);
                    break;
                case "performancerating":
                    employee.setPerformanceRating((Double) newValue);
                    break;
                case "yearsofexperience":
                    employee.setYearsOfExperience((Integer) newValue);
                    break;
                case "isactive":
                    employee.setActive((Boolean) newValue);
                    break;
                default:
                    return false;
            }
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }

    /**
     * Retrieves all employees.
     *
     * @return A list of all employees
     */
    public List<Employee<T>> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }

    /**
     * Searches for employees by department.
     *
     * @param department The department to search for
     * @return A list of employees in the specified department
     */
    public List<Employee<T>> searchByDepartment(String department) {
        return employees.values().stream()
                .filter(emp -> emp.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    /**
     * Searches for employees by name (partial match).
     *
     * @param name The name or part of the name to search for
     * @return A list of employees whose names contain the search term
     */
    public List<Employee<T>> searchByName(String name) {
        return employees.values().stream()
                .filter(emp -> emp.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Filters employees by minimum performance rating.
     *
     * @param minRating The minimum performance rating
     * @return A list of employees with performance rating >= minRating
     */
    public List<Employee<T>> filterByMinimumRating(double minRating) {
        return employees.values().stream()
                .filter(emp -> emp.getPerformanceRating() >= minRating)
                .collect(Collectors.toList());
    }

    /**
     * Filters employees by salary range.
     *
     * @param minSalary The minimum salary
     * @param maxSalary The maximum salary
     * @return A list of employees with salary in the specified range
     */
    public List<Employee<T>> filterBySalaryRange(double minSalary, double maxSalary) {
        return employees.values().stream()
                .filter(emp -> emp.getSalary() >= minSalary && emp.getSalary() <= maxSalary)
                .collect(Collectors.toList());
    }

    /**
     * Custom filtering method using a predicate.
     *
     * @param predicate The filtering predicate
     * @return A list of employees that match the predicate
     */
    public List<Employee<T>> filterEmployees(Predicate<Employee<T>> predicate) {
        return employees.values().stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    /**
     * Gets an iterator for all employees.
     *
     * @return An iterator for the employees
     */
    public Iterator<Employee<T>> getEmployeeIterator() {
        return employees.values().iterator();
    }

    /**
     * Sorts employees by salary (highest first).
     *
     * @return A list of employees sorted by salary in descending order
     */
    public List<Employee<T>> sortBySalary() {
        List<Employee<T>> sortedEmployees = new ArrayList<>(employees.values());
        sortedEmployees.sort(new EmployeeSalaryComparator<>());
        return sortedEmployees;
    }

    /**
     * Sorts employees by performance rating (best first).
     *
     * @return A list of employees sorted by performance rating in descending order
     */
    public List<Employee<T>> sortByPerformance() {
        List<Employee<T>> sortedEmployees = new ArrayList<>(employees.values());
        sortedEmployees.sort(new EmployeePerformanceComparator<>());
        return sortedEmployees;
    }

    /**
     * Sorts employees by years of experience (most experienced first).
     *
     * @return A list of employees sorted by years of experience in descending order
     */
    public List<Employee<T>> sortByExperience() {
        List<Employee<T>> sortedEmployees = new ArrayList<>(employees.values());
        Collections.sort(sortedEmployees); // Uses the natural ordering defined by compareTo
        return sortedEmployees;
    }

    /**
     * Gives a salary raise to employees with high performance ratings.
     *
     * @param minRating The minimum performance rating required for a raise
     * @param percentageRaise The percentage salary raise
     * @return The number of employees who received a raise
     */
    public int giveSalaryRaise(double minRating, double percentageRaise) {
        int count = 0;
        for (Employee<T> employee : employees.values()) {
            if (employee.getPerformanceRating() >= minRating) {
                double newSalary = employee.getSalary() * (1 + percentageRaise / 100);
                employee.setSalary(newSalary);
                count++;
            }
        }
        return count;
    }

    /**
     * Retrieves the top N highest-paid employees.
     *
     * @param n The number of top employees to retrieve
     * @return A list of the top N highest-paid employees
     */
    public List<Employee<T>> getTopPaidEmployees(int n) {
        return employees.values().stream()
                .sorted(new EmployeeSalaryComparator<>())
                .limit(n)
                .collect(Collectors.toList());
    }

    /**
     * Calculates the average salary of employees in a specific department.
     *
     * @param department The department to calculate the average salary for
     * @return The average salary, or 0 if no employees in the department
     */
    public double getAverageSalaryByDepartment(String department) {
        return employees.values().stream()
                .filter(emp -> emp.getDepartment().equalsIgnoreCase(department))
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0);
    }

    /**
     * Gets an employee by their ID.
     *
     * @param employeeId The ID of the employee to retrieve
     * @return The employee, or null if no employee with the given ID exists
     */
    public Employee<T> getEmployee(T employeeId) {
        return employees.get(employeeId);
    }

    /**
     * Gets the number of employees.
     * @return The number of employees
     */
    public int getEmployeeCount() {
        return employees.size();
    }
}