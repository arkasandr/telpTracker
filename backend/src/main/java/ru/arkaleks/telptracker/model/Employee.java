package ru.arkaleks.telptracker.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EMPLOYEES")
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "employeeId")
public class Employee {

    public Employee(String surname, String firstName, String middleName, String department, int groupNumber, String position, String email, WorkRole role, List<EmployeeRole> employeeRole) {
        this.surname = surname;
        this.firstName = firstName;
        this.middleName = middleName;
        this.department = department;
        this.groupNumber = groupNumber;
        this.position = position;
        this.email = email;
        this.role = role;
        this.employeeRole = employeeRole;
    }

    public Employee(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Employee(String surname, String firstName, String middleName, String department, String position) {
        this.surname = surname;
        this.firstName = firstName;
        this.middleName = middleName;
        this.department = department;
        this.position = position;
    }



    public Employee(String username) {
        this.username = username;
    }


    public Employee(String username, List<EmployeeRole> employeeRole) {
        this.username = username;
        this.employeeRole = employeeRole;
    }

    public Employee(@NonNull String username, @NonNull String password, @NonNull String email, List<EmployeeRole> employeeRole) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.employeeRole = employeeRole;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_ID")
    private long employeeId;

    private String surname;

    private String firstName;

    private String middleName;

    private String department;

    private int groupNumber;

    private String position;

    private String username;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private WorkRole role;


//    @ManyToMany(cascade = {
//            CascadeType.PERSIST,
//            CascadeType.MERGE
//    })
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "employee_task",
            joinColumns = { @JoinColumn(name = "employee_id") },
            inverseJoinColumns = { @JoinColumn(name = "task_id") })
    @ElementCollection
    private Set<Task> tasks = new HashSet<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<EmployeeRole> employeeRole;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<TaskMessage> taskMessage;



    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", surname='" + surname + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", department='" + department + '\'' +
                ", groupNumber=" + groupNumber +
                ", position='" + position + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int result = surname.hashCode();
        result = 31 * result + (tasks != null ? tasks.hashCode() : 0);
        return result;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (!surname.equals(employee.surname)) return false;
        if (!firstName.equals(employee.firstName)) return false;
        if (!middleName.equals(employee.middleName)) return false;
        if (!department.equals(employee.department)) return false;
        if (groupNumber != (employee.groupNumber)) return false;
        if (!position.equals(employee.position)) return false;
        return !(tasks != null ? !tasks.equals(employee.tasks) : employee.tasks != null);
    }
}


