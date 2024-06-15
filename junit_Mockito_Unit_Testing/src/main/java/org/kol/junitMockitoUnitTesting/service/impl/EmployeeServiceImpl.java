package org.kol.junitMockitoUnitTesting.service.impl;

import org.kol.junitMockitoUnitTesting.exception.ResourceNotFoundException;
import org.kol.junitMockitoUnitTesting.model.Employee;
import org.kol.junitMockitoUnitTesting.repository.EmployeeRepository;
import org.kol.junitMockitoUnitTesting.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository employeeRepository;


    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {

        Optional<Employee> saveEmployee=employeeRepository.findByEmail(employee.getEmail());

        if(saveEmployee.isPresent()){
            throw new ResourceNotFoundException("Employee Already Exit"+ employee.getEmail());
        }
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @Override
    public Employee updateEmployee(Employee updateEmployee) {
        return employeeRepository.save(updateEmployee);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }


}
