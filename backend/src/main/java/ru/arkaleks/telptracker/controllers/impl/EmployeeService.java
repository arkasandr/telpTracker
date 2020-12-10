package ru.arkaleks.telptracker.controllers.impl;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.arkaleks.telptracker.controllers.dto.EmployeeDto;
import ru.arkaleks.telptracker.controllers.mapper.EmployeeMapper;
import ru.arkaleks.telptracker.model.Employee;
import ru.arkaleks.telptracker.repository.EmployeeRepository;

import java.util.Optional;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */

@Slf4j
@AllArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    private final CurrentUserService currentUserService;

    @Transactional(readOnly = true)
    public EmployeeDto getCurrentEmployeeInfo() {
        String username = currentUserService.getCurrentEmployeeUsername();
        return employeeMapper.mapToEmployeeDto(employeeRepository.findByUsername(username)
                .orElseGet(() -> {
                    throw new IllegalArgumentException("Извините, имя пользователя \" " + username + " \" не существует!");
                }));
    }


//    @Transactional
//    public EmployeeDto updateProfile(Employee employee) {
//        String username = currentUserService.getCurrentEmployee().getUsername();
//        return employeeMapper.mapToEmployeeDto(employeeRepository
//                .findByUsername(username)
//                .map(x -> {
//                    x.setSurname(employee.getSurname());
//                    x.setFirstName(employee.getFirstName());
//                    x.setMiddleName(employee.getMiddleName());
//                    x.setGroupNumber(employee.getGroupNumber());
//                    x.setDepartment(employee.getDepartment());
//                    x.setPosition(employee.getPosition());
//                    log.info("Данные профиля будут обновлены: " + x.toString());
//                    employeeRepository.updateProfile(x.getEmployeeId(), x.getSurname(), x.getFirstName(),
//                            x.getMiddleName(), x.getDepartment(), x.getGroupNumber());
//                    return x;
//
//
//                })
//                .orElseGet(() -> {
//                    throw new IllegalArgumentException("Извините, имя пользователя \" " + employee.getUsername() + " \" не существует!");
//                }));
//    }

    @Transactional
    public EmployeeDto updateProfile(Employee rawEemployee) {
        log.info("Данные для изменение профиля: " + rawEemployee.toString());
        String username = currentUserService.getCurrentEmployee().getUsername();
        Employee employee = employeeRepository.findByUsername(username)
                .orElseGet(() -> {
                    throw new IllegalArgumentException("Извините, имя пользователя \" " + rawEemployee.getUsername() + " \" не существует!");
                });
        if(!rawEemployee.getSurname().equals("")) {
            employee.setSurname(rawEemployee.getSurname());
        } else {
            employee.setSurname(employee.getSurname());
        }
        if(!rawEemployee.getMiddleName().equals("")) {
            employee.setMiddleName(rawEemployee.getMiddleName());
        } else {
            employee.setMiddleName(employee.getMiddleName());
        }
        if(!rawEemployee.getFirstName().equals("")) {
            employee.setFirstName(rawEemployee.getFirstName());
        } else {
            employee.setFirstName(employee.getFirstName());
        }
        if(!rawEemployee.getDepartment().equals("")) {
            employee.setDepartment(rawEemployee.getDepartment());
        } else {
            employee.setDepartment(employee.getDepartment());
        }
        if(rawEemployee.getGroupNumber() != 0) {
            employee.setGroupNumber(rawEemployee.getGroupNumber());
        } else {
            employee.setGroupNumber(employee.getGroupNumber());
        }
        if(!rawEemployee.getPosition().equals("")) {
            employee.setPosition(rawEemployee.getPosition());
        } else {
            employee.setPosition(employee.getPosition());
        }
        log.info("Данные профиля будут обновлены: " + employee.toString());
        employeeRepository.save(employee);
        return  employeeMapper.mapToEmployeeDto(employee);

//                .findByUsername(username)
//        return employeeMapper.mapToEmployeeDto(employeeRepository
//                .findByUsername(username)
//                .map(x -> {
//                    x.setSurname(employee.getSurname());
//                    x.setFirstName(employee.getFirstName());
//                    x.setMiddleName(employee.getMiddleName());
//                    x.setGroupNumber(employee.getGroupNumber());
//                    x.setDepartment(employee.getDepartment());
//                    x.setPosition(employee.getPosition());
//                    log.info("Данные профиля будут обновлены: " + x.toString());
//                    return employeeRepository.save(x);
//
//
//                })

    }
}
