package org.kol.junitMockitoUnitTesting.controller;

import org.kol.junitMockitoUnitTesting.model.Employee;
import org.kol.junitMockitoUnitTesting.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeService.saveEmployee(employee);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployee();
    }

    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable ("id") Long employeeId){
        return employeeService.getEmployeeById(employeeId)
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployeeById(@PathVariable ("id") Long employeeId, @RequestBody Employee employee){
        return employeeService.getEmployeeById(employeeId)
                .map( savedEmployee -> {
                    savedEmployee.setFirstName("Kishalay");
                    savedEmployee.setLastName("Samanta");
                    savedEmployee.setEmail("kishalay01@gmail.com");

                    Employee updatedEmployee=employeeService.updateEmployee(savedEmployee);

                    return  new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
                })
                .orElseGet(()-> ResponseEntity.notFound().build());

    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable ("id") long employeeId){
        employeeService.deleteEmployee(employeeId);

        return new ResponseEntity<String>("Employee deleted successfully.", HttpStatus.OK);
    }




}
