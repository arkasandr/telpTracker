package ru.arkaleks.telptracker.controllers.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import ru.arkaleks.telptracker.model.EmployeeRole;
import ru.arkaleks.telptracker.model.EmployeeTask;
import ru.arkaleks.telptracker.model.Task;
import ru.arkaleks.telptracker.model.WorkRole;

import java.util.List;
import java.util.Set;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@Getter
@Setter
@NoArgsConstructor
public class EmployeeDto {

    public EmployeeDto(String surname, String firstName, String middleName, String department, int groupNumber, String position, String username, String password, String email) {
        this.surname = surname;
        this.firstName = firstName;
        this.middleName = middleName;
        this.groupNumber = groupNumber;
        this.department = department;
        this.position = position;
        this.email = email;
    }

    public EmployeeDto(String surname, String firstName, String middleName, String department, int groupNumber, String position, String username, String password, String email, WorkRole role) {
        this.surname = surname;
        this.firstName = firstName;
        this.middleName = middleName;
        this.department = department;
        this.groupNumber = groupNumber;
        this.position = position;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public EmployeeDto(String surname, String firstName, String middleName, String department, int groupNumber, String position, String username, String password, String email, List<EmployeeRole> employeeRole) {
        this.surname = surname;
        this.firstName = firstName;
        this.middleName = middleName;
        this.department = department;
        this.groupNumber = groupNumber;
        this.position = position;
        this.username = username;
        this.password = password;
        this.email = email;
        this.employeeRole = employeeRole;
    }

    public EmployeeDto(String surname, String firstName, String middleName, String department, int groupNumber, String position, String username, String password, String email, Set<Task> tasks, List<EmployeeRole> employeeRole) {
        this.surname = surname;
        this.firstName = firstName;
        this.middleName = middleName;
        this.department = department;
        this.groupNumber = groupNumber;
        this.position = position;
        this.username = username;
        this.password = password;
        this.email = email;
        this.tasks = tasks;
        this.employeeRole = employeeRole;
    }

    public EmployeeDto(String email) {
        this.email = email;
    }

    public EmployeeDto(long employeeId, String surname, String firstName, String middleName, String department, int groupNumber, String position, String username, String password, String email, Set<Task> tasks, List<EmployeeRole> employeeRole) {
        this.employeeId = employeeId;
        this.surname = surname;
        this.firstName = firstName;
        this.middleName = middleName;
        this.department = department;
        this.groupNumber = groupNumber;
        this.position = position;
        this.username = username;
        this.password = password;
        this.email = email;
        this.tasks = tasks;
        this.employeeRole = employeeRole;
    }

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

    private WorkRole role;

    @JsonIgnore
    private Set<Task> tasks;

    private List<EmployeeRole> employeeRole;

}
