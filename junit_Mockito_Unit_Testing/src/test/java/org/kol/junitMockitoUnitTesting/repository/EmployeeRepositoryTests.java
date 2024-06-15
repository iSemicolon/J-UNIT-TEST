package org.kol.junitMockitoUnitTesting.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kol.junitMockitoUnitTesting.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    EmployeeRepository employeeRepository;

    //junit test for save Employee Operation

    @DisplayName("save Employee Operation")
    @Test
    public void giveEmployeeObject_whenSave_thenReturnSaveEmployee(){

        //given- precondition or setup

        Employee employee=Employee.builder()
                                  .firstName("Palash")
                                  .lastName("Samanta")
                                  .email("palash.samanta7497@gmail.com")
                                  .build();

        //when- action or behaviour of method
        Employee saveEmployee=employeeRepository.save(employee);


        //then- verify the output
        Assertions.assertThat(saveEmployee).isNotNull();
        Assertions.assertThat(saveEmployee.getId()).isGreaterThan(0);

    }


    //find all employee details

    @DisplayName("find all employee details")
    @Test
    public void giveEmployeeList_whenFindAll_thenEmployeeList(){

        //give- precondition

        Employee employee1= Employee.builder()
                                    .firstName("Palash")
                                    .lastName("Samanta")
                                    .email("palash.samanta7497@gmail.com")
                                    .build();

        Employee employee2= Employee.builder()
                .firstName("Kalyan")
                .lastName("Jana")
                .email("janakalyna01@gmail.com")
                .build();

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);



        //when- action or behaviour
        List<Employee> employeeList=employeeRepository.findAll();


        //then- verify output
        Assertions.assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList.size()).isEqualTo(2);
    }

    //get Employee by id

    @DisplayName("get Employee by Id")
    @Test
    public void giveEmployeeList_whenFindById_thenEmployee(){

        //give
        Employee employee1= Employee.builder()
                .firstName("Palash")
                .lastName("Samanta")
                .email("palash.samanta7497@gmail.com")
                .build();

        Employee employee2= Employee.builder()
                .firstName("Kalyan")
                .lastName("Jana")
                .email("janakalyna01@gmail.com")
                .build();


        employeeRepository.save(employee1);
        employeeRepository.save(employee2);


        //when
        Employee employee=employeeRepository.findById(employee1.getId()).get();


        //then
        Assertions.assertThat(employee).isNotNull();

    }

    //get Employee by id

    @DisplayName("get Employee by EmailId")
    @Test
    public void giveEmployeeList_whenFindByEmailId_thenEmployee(){

        //give
        Employee employee1= Employee.builder()
                .firstName("Palash")
                .lastName("Samanta")
                .email("palash.samanta7497@gmail.com")
                .build();

        Employee employee2= Employee.builder()
                .firstName("Kalyan")
                .lastName("Jana")
                .email("janakalyna01@gmail.com")
                .build();


        employeeRepository.save(employee1);
        employeeRepository.save(employee2);


        //when
        Employee employee=employeeRepository.findByEmail(employee2.getEmail()).get();


        //then
        Assertions.assertThat(employee).isNotNull();

    }

    //get Employee by id

    @DisplayName("Update Employee by Id")
    @Test
    public void giveEmployeeId_whenUpdateEmployee_thenReturnEmployee(){

        //give
        Employee employee1= Employee.builder()
                .firstName("Palash")
                .lastName("Samanta")
                .email("palash.samanta7497@gmail.com")
                .build();

        Employee employee2= Employee.builder()
                .firstName("Kalyan")
                .lastName("Jana")
                .email("janakalyna01@gmail.com")
                .build();


        employeeRepository.save(employee1);
        employeeRepository.save(employee2);


        //when
        Employee savedEmployee=employeeRepository.findByEmail(employee2.getEmail()).get();
        savedEmployee.setFirstName("Kishalay");
        savedEmployee.setLastName("Samanta");
        Employee updatedEmployee=employeeRepository.save(savedEmployee);


        //then
        Assertions.assertThat(updatedEmployee.getFirstName()).isEqualTo("Kishalay");
        Assertions.assertThat(updatedEmployee.getLastName()).isEqualTo("Samanta");
        Assertions.assertThat(updatedEmployee.getEmail()).isEqualTo("janakalyna01@gmail.com");

    }

    @DisplayName("Delete Employee by Id")
    @Test
    public void giveEmployeeId_whenDeleteEmployeeById_thenReturnNoEmployee(){

        //give
        Employee employee1= Employee.builder()
                .firstName("Palash")
                .lastName("Samanta")
                .email("palash.samanta7497@gmail.com")
                .build();

        Employee employee2= Employee.builder()
                .firstName("Kishalay")
                .lastName("Samanta")
                .email("kishalay01@gmail.com")
                .build();


        employeeRepository.save(employee1);
        employeeRepository.save(employee2);


        //when
        Employee savedEmployee=employeeRepository.findById(employee2.getId()).get();

        employeeRepository.deleteById(employee2.getId());

        Optional<Employee> deletedEmployee=employeeRepository.findById(employee2.getId());


        //then
        Assertions.assertThat(savedEmployee.getFirstName()).isEqualTo("Kishalay");
        Assertions.assertThat(deletedEmployee).isEmpty();


    }




}
