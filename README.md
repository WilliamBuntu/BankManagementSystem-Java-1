# Employee Management System

A Java application that enables a company to add, manage, search, filter, and sort employees using the Java Collections Framework & Generics.

## Project Overview

This Employee Management System demonstrates the practical implementation of:
- Java Collections Framework (Lists, Sets, Maps)
- Generics for type-safe data structures
- Stream API and Iterators for searching and filtering
- Comparable and Comparator interfaces for custom sorting
- CRUD operations on employee records
- JavaFX user interface for visual management

## Features

- **Employee Management**: Add, remove, update, and view employee records
- **Searching & Filtering**: 
  - Search employees by department or name
  - Filter employees by performance rating or salary range
- **Sorting**:
  - By years of experience (default ordering)
  - By salary (highest first)
  - By performance rating (best first)
- **Salary Management**:
  - Give raises to high-performing employees
  - View top-paid employees
  - Calculate average salary by department
- **User Interface**:
  - JavaFX GUI for easy interaction
  - Displaying, adding, removing, searching, and sorting employees

## Project Structure

The project consists of the following key classes:

- `Employee<T>`: Generic class representing an employee with various attributes
- `EmployeeDatabase<T>`: Manages employee records using Collections
- `EmployeeSalaryComparator<T>` and `EmployeePerformanceComparator<T>`: Custom comparators for sorting
- `EmployeeManagementSystemApp`: JavaFX application for the user interface
- `EmployeeManagementDemo`: Console-based demo showing all features without GUI

## How to Run

### Prerequisites

- Java Development Kit (JDK) 24 or higher
- JavaFX SDK 11 or higher



### Running the Console Demo

1. Compile the code:
```bash
javac employeemanagementsystem/*.java
```

2. Run the demo:
```bash
java employeemanagementsystem.EmployeeManagementDemo
```

## Using the Application

### GUI Application

1. **View Employees**: The main screen displays all employees in the system
2. **Add Employee**: Click "Add Employee" and fill in the employee details
3. **Remove Employee**: Select an employee and click "Remove Employee"
4. **Update Employee**: Select an employee and click "Update Employee"
5. **Search & Filter**:
   - Enter a name or department to search
   - Use the rating slider to filter by minimum rating
6. **Sort**: Select a sort option and click "Sort"
7. **Reset View**: Click "Show All" to display all employees

### Console Demo

The console demo demonstrates all system functionality without requiring a graphical interface. It creates sample data and shows how to:

- Add employees
- Search and filter employees
- Sort employees
- Calculate statistics
- Update and remove employees



