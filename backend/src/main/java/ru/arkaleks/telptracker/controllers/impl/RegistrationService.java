package ru.arkaleks.telptracker.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.arkaleks.telptracker.controllers.dto.EmployeeDto;
import ru.arkaleks.telptracker.controllers.mapper.EmployeeMapper;
import ru.arkaleks.telptracker.model.Employee;
import ru.arkaleks.telptracker.model.EmployeeRole;
import ru.arkaleks.telptracker.repository.EmployeeRepository;

import java.util.Arrays;
import java.util.List;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@RequiredArgsConstructor
@Transactional
@Service
public class RegistrationService {

    private final EmployeeRepository employeeRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final CurrentUserService currentUserService;

    private final EmployeeMapper employeeMapper;

    /**
     * Метод сохраняет нового сотрудника Employee без роли EmployeeRole
     */
    public void saveEmployeeWithoutEmployeeRole(Employee newEmployee) {
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            if (employee.getUsername() != null) {
                if (employee.getUsername().equals(newEmployee.getUsername())) {
                    throw new IllegalArgumentException("Извините, имя пользователя \" = " + employee.getUsername() + " \" уже существует!");
                }
            } else {
                throw new IllegalArgumentException("Извините, имя пользователя не задано!");
            }
        }
        Employee addEmployee = new Employee(newEmployee.getUsername(), newEmployee.getPassword(), newEmployee.getEmail());
        addEmployee.setPassword(bCryptPasswordEncoder.encode(newEmployee.getPassword()));
        employeeRepository.save(addEmployee);
    }


//    /**
//     * Метод устанавливает роль USER для добавляемого сотрудника Employee
//     */
//    public void setUserRoleToEmployee(Employee newEmployee) {
//        List<EmployeeRole> roles = Collections.singletonList(new EmployeeRole("ROLE_USER"));
//        Optional<Employee> optionalEmployee = employeeRepository.findByUsername(newEmployee.getUsername());
//        Employee employee = optionalEmployee.orElseGet(Employee::new);
//        roles.get(0).setEmployee(employee);
//    }
//
//    /**
//     * Метод добавляет нового пользователя сотрудника Employee в приложение
//     */
//    public EmployeeDto addNewEmployee(Employee newEmployee) {
//        Optional<Employee> optionalEmployee = employeeRepository.findByUsername(newEmployee.getUsername());
//        Employee employee = optionalEmployee.orElseGet(Employee::new);
//        return employeeMapper.mapToEmployeeDto(employee);
//    }


    /**
     * Метод обновляет данные сотрудника Employee
     */
    public EmployeeDto updateEmployeeByUsername(Employee employee) {
        String username = currentUserService.getCurrentEmployee().getUsername();
        return employeeMapper.mapToEmployeeDto(employeeRepository
                .findByUsername(username)
                .map(x -> {
                    x.setGroupNumber(employee.getGroupNumber());
                    x.setSurname(employee.getSurname());
                    x.setFirstName(employee.getFirstName());
                    x.setMiddleName(employee.getMiddleName());
                    x.setDepartment(employee.getDepartment());
                    x.setPosition(employee.getPosition());
                    return employeeRepository.save(x);
                })
                .orElseGet(() -> {
                    throw new IllegalArgumentException("Извините, имя пользователя \" " + employee.getUsername() + " \" не существует!");
                }));
    }


    /**
     * Метод устанавливает роль EmployeeRole для добавляемого сотрудника Employee
     *
     * @param
     * @return
     * @throws
     */
    public void setEmployeeRoleToEmployee(Employee newEmployee) {
        List<EmployeeRole> roles = newEmployee.getEmployeeRole();
        roles.get(0).setEmployee(employeeRepository.findByUsername(newEmployee.getUsername()).get());
        employeeRepository.findByUsername(newEmployee.getUsername()).get().setEmployeeRole(roles);
    }

    /**
     * Метод устанавливает роль USER для добавляемого сотрудника Employee
     *
     * @param
     * @return
     * @throws
     */
    public void setUserRoleToEmployee(Employee newEmployee) {
        List<EmployeeRole> roles = Arrays.asList(new EmployeeRole("ROLE_USER"));
        roles.get(0).setEmployee(employeeRepository.findByUsername(newEmployee.getUsername()).get());
        employeeRepository.findByUsername(newEmployee.getUsername()).get().setEmployeeRole(roles);
    }

    /**
     * Метод добавляет нового пользователя сотрудника Employee в приложение
     *
     * @param
     * @return EmployeeDTO
     * @throws
     */
    public EmployeeDto addNewEmployee(Employee newEmployee) {
        return employeeMapper.mapToEmployeeDto(employeeRepository.findByUsername(newEmployee.getUsername()).get());
    }


}
