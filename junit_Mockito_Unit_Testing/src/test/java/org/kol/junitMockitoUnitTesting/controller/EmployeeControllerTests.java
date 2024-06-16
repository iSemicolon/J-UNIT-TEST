package org.kol.junitMockitoUnitTesting.controller;


import com.fasterxml.jackson.databind.ObjectMapper;


import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.kol.junitMockitoUnitTesting.model.Employee;
import org.kol.junitMockitoUnitTesting.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    EmployeeService employeeService;

    Employee employee1;
    Employee employee2;

    @Autowired
    ObjectMapper objectMapper;


    //Create request

    @DisplayName("save employee")
    @Test
    public void givenEmployee_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {

        //given- pre-conditions or setup

        employee1= Employee.builder()
                .id(1L)
                .firstName("Palash")
                .lastName("Samanta")
                .email("palash.samanta7497@gmail.com")
                .build();



        given(employeeService.saveEmployee(any(Employee.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        //when- action or behaviour going to test


        ResultActions resultActions = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee1)));

        //then- verify the test result or output using assert statements
        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
                        CoreMatchers.is(employee1.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",
                        CoreMatchers.is(employee1.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email",
                        CoreMatchers.is(employee1.getEmail())));

    }



    @DisplayName("fetch all employee")
    @Test
    public void givenEmployee_whenGetAllEmployees_thenReturnSavedEmployee() throws Exception {

        //given- pre-conditions or setup

        List<Employee> employeeList=new ArrayList<>();

        employee1= Employee.builder()
                .id(1L)
                .firstName("Palash")
                .lastName("Samanta")
                .email("palash.samanta7497@gmail.com")
                .build();

        employee2=Employee.builder()
                .id(2L)
                .firstName("Kishalay")
                .lastName("Samanta")
                .email("kishalay01@gmail.com")
                .build();

        employeeList.add(employee1);
        employeeList.add(employee2);

        given(employeeService.getAllEmployee()).willReturn(employeeList);

        //when- action or behaviour going to test


        ResultActions resultActions = mockMvc.perform(get("/api/employees"));

        //then- verify the test result or output using assert statements
        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        CoreMatchers.is(employeeList.size())));

    }


    //get employee by Id-positive scenario

    @DisplayName("get employee by Id")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployee() throws Exception {

        //given- pre-conditions or setup

        List<Employee> employeeList=new ArrayList<>();

        employee1= Employee.builder()
                .id(1L)
                .firstName("Palash")
                .lastName("Samanta")
                .email("palash.samanta7497@gmail.com")
                .build();

        employee2=Employee.builder()
                .id(2L)
                .firstName("Kishalay")
                .lastName("Samanta")
                .email("kishalay01@gmail.com")
                .build();

        employeeList.add(employee1);
        employeeList.add(employee2);

        Long employeeId=1L;

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee1));



        //when- action or behaviour going to test



        ResultActions resultActions = mockMvc.perform(get("/api/employees/{id}", employeeId));

        //then- verify the test result or output using assert statements
        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
                        CoreMatchers.is(employee1.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",
                        CoreMatchers.is(employee1.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email",
                        CoreMatchers.is(employee1.getEmail())));

    }



    //get employee by Id-positive scenario

    @DisplayName("get employee by Id for Invalid response")
    @Test
    public void givenWrongEmployeeId_whenGetEmployeeById_thenInvalidResponse() throws Exception {

        //given- pre-conditions or setup

        List<Employee> employeeList=new ArrayList<>();

        employee1= Employee.builder()
                .id(1L)
                .firstName("Palash")
                .lastName("Samanta")
                .email("palash.samanta7497@gmail.com")
                .build();

        employee2=Employee.builder()
                .id(2L)
                .firstName("Kishalay")
                .lastName("Samanta")
                .email("kishalay01@gmail.com")
                .build();

        employeeList.add(employee1);
        employeeList.add(employee2);

        Long employeeId=1L;

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());



        //when- action or behaviour going to test



        ResultActions resultActions = mockMvc.perform(get("/api/employees/{id}", employeeId));

        //then- verify the test result or output using assert statements
        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());


    }

    //Update Employee id -positive scenario
    @DisplayName("update employee by Id ")
    @Test
    public void givenEmployeeId_whenUpdateEmployeeById_thenReturnUpdateEmployee() throws Exception {

        //given- pre-conditions or setup

       Employee savedEmployee1= Employee.builder()
                .id(1L)
                .firstName("Palash")
                .lastName("Samanta")
                .email("palash.samanta7497@gmail.com")
                .build();

        Employee updatedEmployee2=Employee.builder()
                .id(2L)
                .firstName("Kishalay")
                .lastName("Samanta")
                .email("kishalay01@gmail.com")
                .build();

        Long employeeId=1L;

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(savedEmployee1));
        given(employeeService.updateEmployee(any(Employee.class)))
                .willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));

        //when- action or behaviour going to test

        ResultActions resultActions = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee2)));

        //then- verify the test result or output using assert statements
        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
                        CoreMatchers.is(updatedEmployee2.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",
                        CoreMatchers.is(updatedEmployee2.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email",
                        CoreMatchers.is(updatedEmployee2.getEmail())));


    }


    //Update Employee id -negative scenario
    @DisplayName("update employee by Id not Found ")
    @Test
    public void givenEmployee_whenUpdateEmployeeById_thenReturn404() throws Exception {

        //given- pre-conditions or setup

        Employee savedEmployee1= Employee.builder()
                .id(1L)
                .firstName("Palash")
                .lastName("Samanta")
                .email("palash.samanta7497@gmail.com")
                .build();

        Employee updatedEmployee2=Employee.builder()
                .id(2L)
                .firstName("Kishalay")
                .lastName("Samanta")
                .email("kishalay01@gmail.com")
                .build();

        Long employeeId=1L;

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());
        given(employeeService.updateEmployee(any(Employee.class)))
                .willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));

        //when- action or behaviour going to test

        ResultActions resultActions = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee2)));

        //then- verify the test result or output using assert statements
        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());


    }



    //Delete Employee
    @DisplayName("Delete employee  ")
    @Test
    public void givenEmployee_whenDeleteEmployee_thenReturn200() throws Exception {

        //given- pre-conditions or setup

        Long employeeId=1L;

        willDoNothing().given(employeeService).deleteEmployee(employeeId);


        //when- action or behaviour going to test

        ResultActions resultActions = mockMvc.perform(delete("/api/employees/{id}", employeeId));


        //then- verify the test result or output using assert statements
        resultActions.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }


}


