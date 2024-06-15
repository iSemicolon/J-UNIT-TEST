package org.kol.junitMockitoUnitTesting.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kol.junitMockitoUnitTesting.model.Employee;
import org.kol.junitMockitoUnitTesting.repository.EmployeeRepository;
import org.kol.junitMockitoUnitTesting.service.impl.EmployeeServiceImpl;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeServiceImpl employeeServiceImpl;

    Employee employee1;

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
}
