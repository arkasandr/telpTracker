package ru.arkaleks.telptracker.controllers;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.arkaleks.telptracker.controllers.dto.TaskMessageDto;
import ru.arkaleks.telptracker.controllers.impl.TaskMessageService;
import ru.arkaleks.telptracker.model.TaskMessage;

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
}
