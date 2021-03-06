package ru.arkaleks.telptracker.controllers.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;
import ru.arkaleks.telptracker.controllers.dto.EmployeeDto;
import ru.arkaleks.telptracker.controllers.dto.EmployeeRoleDto;
import ru.arkaleks.telptracker.controllers.dto.TaskDto;
import ru.arkaleks.telptracker.model.Employee;
import ru.arkaleks.telptracker.model.EmployeeRole;
import ru.arkaleks.telptracker.model.Task;

import java.util.List;
import java.util.Set;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@Service
@Mapper
public interface EmployeeMapper {

    EmployeeDto mapToEmployeeDto(Employee employeeEntity);

    List<EmployeeDto> mapToListEmployeeDto(List<Employee> employeeList);

    Set<EmployeeDto> mapToSetEmployeeDto(Set<Employee> employeeSet);

    Set<TaskDto> mapToSetTaskDto(Set<Task> taskSet);

    EmployeeRoleDto mapToEmployeeRoleDto (EmployeeRole employeeRole);

    List<EmployeeRoleDto> mapToListEmployeeRoleDto (List<EmployeeRole> EmployeeRoleSet);

}
