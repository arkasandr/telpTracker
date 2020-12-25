package ru.arkaleks.telptracker.controllers;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.arkaleks.telptracker.controllers.dto.EmployeeDto;
import ru.arkaleks.telptracker.controllers.dto.TaskListDto;
import ru.arkaleks.telptracker.controllers.dto.TaskMessageDto;
import ru.arkaleks.telptracker.controllers.impl.TaskMessageService;
import ru.arkaleks.telptracker.model.TaskMessage;

import java.util.List;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@Slf4j
@AllArgsConstructor
@RestController
public class TaskMessageController {

    private final TaskMessageService taskMessageService;


    /**
     * Метод добавляет новое сообщение
     */

    @PostMapping("/api/tasks/message/add")
    TaskMessageDto addNewMessageToTask(@RequestBody TaskMessage message) {
        return taskMessageService.addNewMessageToTask(message);
    }

    /**
     * Метод вовращает все сообщения к задаче
     */
    @PostMapping("api/tasks/message/{taskId}/getall")
    List<TaskMessageDto> getAllTaskMessages(@PathVariable long taskId) {
        return taskMessageService.getAllTaskMessages(taskId);
    }

    /**
     * Метод вовращает отправителя сообщения
     */
    @PostMapping("api/tasks/message/{messageId}/getsender")
    EmployeeDto getMessageSender(@PathVariable long messageId) {
        return taskMessageService.getMessageSender(messageId);
    }
}
