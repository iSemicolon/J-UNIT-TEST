package org.kol.junitMockitoUnitTesting.repository;

import org.kol.junitMockitoUnitTesting.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

     Optional<Employee> findByEmail(String email);

     //JPQL Query for index  based
     @Query("SELECT e FROM Employee e WHERE e.firstName = ?1 AND e.lastName = ?2")
     Employee findByJPQL(String firstName, String lastName);

     //JPQL Query for  Name param based

    @Query("SELECT e FROM  Employee e WHERE e.firstName= :first_Name AND e.lastName= :last_Name")
     Employee findByNameParamBasedJPQL(@Param("first_Name") String firstName, @Param("last_Name") String lastName);

     //JPQL NativeQuery with index  based

    @Query(value = "select * from employees e where e.first_name =?1 and e.last_name =?2", nativeQuery = true)
    Employee findByNativeSQLquery(String firstName, String lastName);

    // define custom query using Native SQL with named params
    @Query(value = "select * from employees e where e.first_name =:firstName and e.last_name =:lastName", nativeQuery = true)
    Employee findByNativeSQLNamedParam(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
