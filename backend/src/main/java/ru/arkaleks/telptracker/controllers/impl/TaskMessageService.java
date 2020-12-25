package ru.arkaleks.telptracker.controllers.impl;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.arkaleks.telptracker.controllers.dto.EmployeeDto;
import ru.arkaleks.telptracker.controllers.dto.TaskMessageDto;
import ru.arkaleks.telptracker.controllers.mapper.EmployeeMapper;
import ru.arkaleks.telptracker.controllers.mapper.MessageMapper;
import ru.arkaleks.telptracker.model.Employee;
import ru.arkaleks.telptracker.model.Task;
import ru.arkaleks.telptracker.model.TaskMessage;
import ru.arkaleks.telptracker.repository.EmployeeRepository;
import ru.arkaleks.telptracker.repository.TaskMessageRepository;
import ru.arkaleks.telptracker.repository.TaskRepository;

import java.util.ArrayList;
import java.util.Comparator;
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

    private final EmployeeMapper employeeMapper;

    @Transactional
    public TaskMessageDto addNewMessageToTask(TaskMessage message) {
        message.setSender(getLoginEmployee());
        long taskId = message.getTaskNumber();
        Task task = taskRepository.findTaskByTaskId(taskId);
        message.setTask(task);
        taskMessageRepository.save(message);
        log.info("New message is: " + message);
        return messageMapper.mapToTaskMessageDto(message);
    }

    @Transactional(readOnly = true)
    public List<TaskMessageDto> getAllTaskMessages(long taskId) {
        List<TaskMessage> result = new ArrayList<>();
        Task task = taskRepository.findTaskByTaskId(taskId);
        result.addAll(task.getMessages());
        result.sort(new MessageComparator());
        log.info("Task with taskId = " + taskId + " has " + result.size() + " messages");
        return messageMapper.mapToTaskMessageDtoList(result);
    }


    @Transactional(readOnly = true)
    public EmployeeDto getMessageSender(long messageId) {
        Employee result = taskMessageRepository.findTaskMessageByMessageId(messageId).getSender();
        log.info("Message sender is = " + result.toString());
        return employeeMapper.mapToEmployeeDto(result);
    }

    private Employee getLoginEmployee() {
        String emplName = currentUserService.getCurrentEmployee().getUsername();
        Optional<Employee> optionalEmployee = employeeRepository.findByUsername(emplName);
        return optionalEmployee.orElseGet(Employee::new);
    }


    static class MessageComparator implements Comparator<TaskMessage> {
        @Override
        public int compare(TaskMessage o1, TaskMessage o2) {
            return o2.getSpendDate().compareTo(o1.getSpendDate());
        }
    }

}
