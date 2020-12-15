package ru.arkaleks.telptracker.controllers;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.arkaleks.telptracker.controllers.dto.EmployeeDto;
import ru.arkaleks.telptracker.controllers.impl.EmployeeService;
import ru.arkaleks.telptracker.model.Employee;

import java.util.List;


/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@Slf4j
@AllArgsConstructor
@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Метод вовращает информацию о сотруднике Employee
     */
    @GetMapping("api/employee/info")
    EmployeeDto getEmployeeInfoForProfile() {
        return employeeService.getCurrentEmployeeInfo();
    }

    /**
     * Метод вовращает список всех сотруднков
     */
    @GetMapping("api/employee/getall")
    List<String> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    /**
     * Метод вовращает одного сотруднка по ФИО
     */
    @PostMapping("api/employee/getbyfio")
    EmployeeDto getEmployeeByFio(@RequestBody String[] array) {
        return employeeService.getEmployeeByFio(array);
    }

    @PostMapping("api/employee/changeinfo")
    EmployeeDto updateEmployeeProfile(@RequestBody Employee employee) {
        return employeeService.updateProfile(employee);
    }
}
