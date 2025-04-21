package com.example.employeemanagementsystem.utils;

import java.util.Comparator;

import com.example.employeemanagementsystem.model.Employee;

/**
 * Comparator to sort employees by salary in descending order.
 *
 * @param <T> Type of the employee ID
 */
public class EmployeeSalaryComparator<T> implements Comparator<Employee<T>> {
    @Override
    public int compare(Employee<T> e1, Employee<T> e2) {
        return Double.compare(e2.getSalary(), e1.getSalary());
    }
}

