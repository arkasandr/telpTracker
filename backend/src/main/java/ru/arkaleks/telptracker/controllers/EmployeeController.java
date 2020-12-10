package ru.arkaleks.telptracker.controllers;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.arkaleks.telptracker.controllers.dto.EmployeeDto;
import ru.arkaleks.telptracker.controllers.impl.EmployeeService;
import ru.arkaleks.telptracker.model.Employee;

import java.io.IOException;
import java.text.ParseException;

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


    @PostMapping("api/employee/changeinfo")
    EmployeeDto updateEmployeeProfile(@RequestBody Employee employee) {
        return employeeService.updateProfile(employee);
    }
}
