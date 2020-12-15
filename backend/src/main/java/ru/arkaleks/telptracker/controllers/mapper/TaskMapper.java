package ru.arkaleks.telptracker.controllers.mapper;


import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;
import ru.arkaleks.telptracker.controllers.dto.EmployeeDto;
import ru.arkaleks.telptracker.controllers.dto.TaskDto;
import ru.arkaleks.telptracker.model.Employee;
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
public interface TaskMapper {

    TaskDto mapToTaskDto(Task task);

    Task mapToTask(TaskDto taskDto);

    List<TaskDto> mapToTaskDtoList(List<Task> taskList);

    Set<TaskDto> mapToSetTaskDto(Set<Task> taskSet);

    Set<Task> mapToSetTask(Set<TaskDto> taskDtoSet);

    Set<EmployeeDto> mapToSetEmployeeDto(Set<Employee> employeeSet);
}
