package ru.arkaleks.telptracker.model;

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
public class Employee {

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

//    @ManyToMany(mappedBy = "employee", cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "Tasks",
//            joinColumns = { @JoinColumn(name = "employee_id") },
//            inverseJoinColumns = { @JoinColumn(name = "task_id") }
//    )


    @OneToMany(mappedBy = "employeeId", cascade = CascadeType.ALL)
    private Set<EmployeeTask> tasks = new HashSet<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<EmployeeRole> employeeRole;

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
}
