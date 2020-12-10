package ru.arkaleks.telptracker.controllers.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;
import ru.arkaleks.telptracker.controllers.dto.EmployeeDto;
import ru.arkaleks.telptracker.model.Employee;

import java.util.List;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@Service
@Mapper
public interface EmployeeMapper {

    EmployeeDto mapToEmployeeDto(Employee employeeEntity);

}
