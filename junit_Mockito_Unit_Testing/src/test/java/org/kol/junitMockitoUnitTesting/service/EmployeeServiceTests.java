package org.kol.junitMockitoUnitTesting.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kol.junitMockitoUnitTesting.model.Employee;
import org.kol.junitMockitoUnitTesting.repository.EmployeeRepository;
import org.kol.junitMockitoUnitTesting.service.impl.EmployeeServiceImpl;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.Optional;

//@SpringBootTest
public class EmployeeServiceTests {

    EmployeeRepository employeeRepository;
    EmployeeService employeeService;
    Employee employee1;

    @BeforeEach
    public void setup() {
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    @DisplayName("save employee")
    @Test
    public void givenEmployee_whenSaveEmployee_thenReturnEmployee(){

        //given- precondition or setup
                  employee1=Employee.builder()
                                    .id(1L)
                                    .firstName("Palash")
                                    .lastName("Samanta")
                                    .email("palash.samanta7497@gmail.com")
                                    .build();

        BDDMockito.given(employeeRepository.findByEmail(employee1.getEmail())).willReturn(Optional.empty());

        BDDMockito.given(employeeRepository.save(employee1)).willReturn(employee1);


        //when- behaviour or function
         Employee savedEmployee=employeeService.saveEmployee(employee1);


        //then- output
        Assertions.assertThat(savedEmployee).isNotNull();

    }
}
