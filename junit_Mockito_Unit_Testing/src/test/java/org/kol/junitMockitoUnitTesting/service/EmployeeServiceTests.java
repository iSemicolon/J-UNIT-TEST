package org.kol.junitMockitoUnitTesting.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kol.junitMockitoUnitTesting.exception.ResourceNotFoundException;
import org.kol.junitMockitoUnitTesting.model.Employee;
import org.kol.junitMockitoUnitTesting.repository.EmployeeRepository;
import org.kol.junitMockitoUnitTesting.service.impl.EmployeeServiceImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeServiceImpl employeeServiceImpl;

    Employee employee1;
    Employee employee2;

    @BeforeEach
    public void setup() {
       // employeeRepository = Mockito.mock(EmployeeRepository.class);
        // employeeService = new EmployeeServiceImpl(employeeRepository);

        employee1=Employee.builder()
                .id(1L)
                .firstName("Palash")
                .lastName("Samanta")
                .email("palash.samanta7497@gmail.com")
                .build();
    }

    // save Employee
    @DisplayName("save employee")
    @Test
    public void givenEmployee_whenSaveEmployee_thenReturnEmployee(){

        //given- precondition or setup

        /*
                  employee1=Employee.builder()
                                    .id(1L)
                                    .firstName("Palash")
                                    .lastName("Samanta")
                                    .email("palash.samanta7497@gmail.com")
                                    .build();

*/
       given(employeeRepository.findByEmail(employee1.getEmail())).willReturn(Optional.empty());

       given(employeeRepository.save(employee1)).willReturn(employee1);


        //when- behaviour or function
         Employee savedEmployee=employeeServiceImpl.saveEmployee(employee1);


        //then- output
        Assertions.assertThat(savedEmployee).isNotNull();

    }


    @DisplayName("If employee email already exists then Throws Exception ")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException(){

        //given- precondition or setup

        given(employeeRepository.findByEmail(employee1.getEmail())).willReturn(Optional.of(employee1));

        //when- behaviour or function
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, ()->{
            Employee savedEmployee=employeeServiceImpl.saveEmployee(employee1);
        });


        //then- output
        verify(employeeRepository, Mockito.never()).save(any(Employee.class));

    }


    //get all employees
    @DisplayName("Get all Employee ")
    @Test
    public void givenEmployee_whenGetAllEmployee_thenReturnAllEmployees(){

        //given- precondition or setup


                  employee2=Employee.builder()
                                    .id(2L)
                                    .firstName("Kishalay")
                                    .lastName("Samanta")
                                    .email("kishalay01@gmail.com")
                                    .build();


        given(employeeRepository.findAll()).willReturn(List.of(employee1,employee2));


        //when- behaviour or function
        List<Employee> savedEmployee=employeeServiceImpl.getAllEmployee();


        //then- output
        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.size()).isEqualTo(2);

    }

    @DisplayName("Get all Employee when List is empty ")
    @Test
    public void givenEmptyEmployee_whenGetAllEmployee_thenReturnEmptyEmployees(){

        //given- precondition or setup

        employee2=Employee.builder()
                .id(2L)
                .firstName("Kishalay")
                .lastName("Samanta")
                .email("kishalay01@gmail.com")
                .build();


        given(employeeRepository.findAll()).willReturn(Collections.emptyList());


        //when- behaviour or function
        List<Employee> savedEmployee=employeeServiceImpl.getAllEmployee();


        //then- output
        Assertions.assertThat(savedEmployee).isEmpty();
        Assertions.assertThat(savedEmployee.size()).isEqualTo(0);

    }

    //get employee details by Id

    @DisplayName("Get Employee ById")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployee(){

        //given-pre setup

        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee1));

        //when -behaviour
        Employee savedEmployee=employeeServiceImpl.getEmployeeById(employee1.getId()).get();

        //then- output
        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getFirstName()).isEqualTo("Palash");
    }

    //Update employee details

    @DisplayName("Update employee details")
    @Test
    public void givenEmployee_whenUpdateEmployee_thenReturnEmployee(){

        //given-pre setup

        given(employeeRepository.save(employee1)).willReturn(employee1);
        employee1.setFirstName("Prabhat");
        employee1.setLastName("prabhat@gmail.com");

        //when -behaviour
        Employee updatedEmployee=employeeServiceImpl.updateEmployee(employee1);

        //then- output
        Assertions.assertThat(updatedEmployee).isNotNull();
        Assertions.assertThat(updatedEmployee.getFirstName()).isEqualTo("Prabhat");
        Assertions.assertThat(updatedEmployee.getLastName()).isEqualTo("prabhat@gmail.com");
    }

    // delete Employee by iD
    @DisplayName("Update employee details")
    @Test
    public void givenEmployeeById_whenDeleteEmployee_thenNothing(){

        //given-pre setup
        Long employeeId=2L;

       willDoNothing().given(employeeRepository).deleteById(employeeId);

        //when -behaviour
        employeeServiceImpl.deleteEmployee(employeeId);

        //then- output
        verify(employeeRepository,times(1)).deleteById(employeeId);
    }



}
