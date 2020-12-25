package ru.arkaleks.telptracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.arkaleks.telptracker.model.Employee;

import java.util.List;
import java.util.Optional;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.employeeRole WHERE e.username = :username")
    Optional<Employee> findByUsername(@Param("username") String username);


    @Query("SELECT e FROM Employee e")
    List<Employee> findAllEmployees();

    @Query("SELECT e FROM Employee e WHERE e.surname = :surname AND e.firstName = :firstName AND e.middleName = :middleName")
    Employee findByFio(String surname, String firstName, String middleName);

//    @Modifying(clearAutomatically = true)
//    @Query("UPDATE Employee e SET e.surname = :surname, e.firstName = :firstName, " +
//            "e.middleName = :middleName, e.department = :department, e.groupNumber = :groupNumber" +
//            "   WHERE e.employeeId = :employeeId and (:surname is not null)")
//    void updateProfile(@Param("employeeId") Long employeeId, @Param("surname") String surname,
//                       @Param("firstName") String firstName, @Param("middleName") String middleName,
//                       @Param("department") String department, @Param("groupNumber") int groupNumber
//                       );


}


