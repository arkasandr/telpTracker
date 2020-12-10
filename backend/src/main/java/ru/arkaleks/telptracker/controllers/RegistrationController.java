package ru.arkaleks.telptracker.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.arkaleks.telptracker.controllers.dto.EmployeeDto;
import ru.arkaleks.telptracker.controllers.impl.CurrentUserService;
import ru.arkaleks.telptracker.controllers.impl.RegistrationService;
import ru.arkaleks.telptracker.model.Employee;


/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@Transactional
public class RegistrationController {

    private final RegistrationService registrationService;

    private final CurrentUserService currentUserService;

    /**
     * Метод регистрирует нового сотрудника Employee
     */
//    @PostMapping("/registrationstart/continue")
    @PostMapping("/api/auth/signup")
    EmployeeDto registerNewEmployee(@RequestBody Employee newEmployee) {
        log.info("Stsrt registration new employee: " + newEmployee);
        registrationService.saveEmployeeWithoutEmployeeRole(newEmployee);
        registrationService.setUserRoleToEmployee(newEmployee);
        return registrationService.addNewEmployee(newEmployee);
    }

    /**
     * Метод обновляет информацию о зарегестрированном сотруднике Employee
     */
    @PostMapping("/registrationend/adddata")
    EmployeeDto addNewEmployeeData(@RequestBody Employee newEmployee) {
        return registrationService.updateEmployeeByUsername(newEmployee);
    }

    /**
     * Метод вовращает имя зарегистрированного сотрудника Employee
     */
    @GetMapping("registrationstart/current")
    String getCurrentEmployeeUsername() {
        return currentUserService.getCurrentEmployeeUsername();
    }
}
