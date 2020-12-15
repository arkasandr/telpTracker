package ru.arkaleks.telptracker.controllers;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.arkaleks.telptracker.controllers.dto.TaskDto;
import ru.arkaleks.telptracker.controllers.dto.TaskListDto;
import ru.arkaleks.telptracker.controllers.impl.TaskService;
import ru.arkaleks.telptracker.model.Task;

import java.util.List;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@Slf4j
@AllArgsConstructor
@RestController
public class TaskController {

    private final TaskService taskService;

    /**
     * Метод добавляет новую задачу
     */

    @PostMapping("/api/tasks/admin/add")
    TaskDto addNewTaskToEmployee(@RequestBody Task task) {
        return taskService.addNewTaskToEmployee(task);
    }


    /**
     * Метод вовращает список всех задач
     */
    @GetMapping("api/tasks/getall")
    List<TaskListDto> getAllTasks() {
        return taskService.getAllEmployeeTasks();
    }

    /**
     * Метод удаляет задачу
     */
    @DeleteMapping("api/tasks/{taskId}/delete")
    void deleteTask(@PathVariable long taskId) {
        taskService.deleteTask(taskId);
    }
}
