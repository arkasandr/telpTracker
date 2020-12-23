package ru.arkaleks.telptracker.controllers.impl;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.arkaleks.telptracker.controllers.dto.TaskMessageDto;
import ru.arkaleks.telptracker.controllers.mapper.MessageMapper;
import ru.arkaleks.telptracker.model.Employee;
import ru.arkaleks.telptracker.model.Task;
import ru.arkaleks.telptracker.model.TaskMessage;
import ru.arkaleks.telptracker.repository.EmployeeRepository;
import ru.arkaleks.telptracker.repository.TaskMessageRepository;
import ru.arkaleks.telptracker.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */

@Slf4j
@AllArgsConstructor
@Service
public class TaskMessageService {

    private final TaskMessageRepository taskMessageRepository;

    private final EmployeeRepository employeeRepository;

    private final TaskRepository taskRepository;

    private CurrentUserService currentUserService;

    private final MessageMapper messageMapper;

    @Transactional
    public TaskMessageDto addNewMessageToTask(TaskMessage message) {
        message.setSender(getLoginEmployee());
        long taskId = message.getTaskNumber();
        Task task = taskRepository.findTaskByTaskId(taskId);
        message.setTask(task);
//        List messages = new ArrayList<>();
//        messages.add(message);
//        task.setMessages(messages);
//        taskRepository.save(task);
        taskMessageRepository.save(message);
        log.info("New message is: " + message);
        return messageMapper.mapToTaskMessageDto(message);
    }

    private Employee getLoginEmployee() {
        String emplName = currentUserService.getCurrentEmployee().getUsername();
        Optional<Employee> optionalEmployee = employeeRepository.findByUsername(emplName);
        return optionalEmployee.orElseGet(Employee::new);
    }

}
