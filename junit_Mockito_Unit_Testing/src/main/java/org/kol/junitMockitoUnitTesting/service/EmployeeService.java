package org.kol.junitMockitoUnitTesting.service;

import org.kol.junitMockitoUnitTesting.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    List<Employee> getAllEmployee();
    Optional<Employee> getEmployeeById(Long employeeId);
    Employee updateEmployee(Employee updateEmployee);
    void deleteEmployee(Long employeeId);
}
