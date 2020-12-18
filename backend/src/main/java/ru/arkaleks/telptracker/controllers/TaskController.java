package ru.arkaleks.telptracker.controllers;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.arkaleks.telptracker.controllers.dto.EmployeeDto;
import ru.arkaleks.telptracker.controllers.dto.TaskDto;
import ru.arkaleks.telptracker.controllers.dto.TaskListDto;
import ru.arkaleks.telptracker.controllers.impl.TaskService;
import ru.arkaleks.telptracker.model.Employee;
import ru.arkaleks.telptracker.model.Task;

import java.util.List;
import java.util.Set;

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

    /**
     * Метод меняет статус задачи на "Начата"
     */
    @PostMapping("/api/tasks/status/{taskId}/start")
    void setTaskStatusToStart (@PathVariable long taskId) {
        taskService.setTaskStatusToStart(taskId);
    }

    /**
     * Метод меняет статус задачи на "Приостановлена"
     */
    @PostMapping("/api/tasks/status/{taskId}/pause")
    void setTaskStatusToPause (@PathVariable long taskId) {
        taskService.setTaskStatusToPause(taskId);
    }

    /**
     * Метод меняет статус задачи на "Закончена"
     */
    @PostMapping("/api/tasks/status/{taskId}/end")
    void setTaskStatusToEnd (@PathVariable long taskId) {
        taskService.setTaskStatusToEnd(taskId);
    }

    /**
     * Метод вовращает список участников задачи
     */
    @PostMapping("api/tasks/members/{taskId}")
    Set<EmployeeDto> getTaskMembers(@PathVariable long taskId) {
        return taskService.getAllEmployeeByTaskId(taskId);
    }

    /**
     * Метод вовращает задачу по taskId
     */
    @PostMapping("api/tasks/current/{taskId}")
    TaskDto getTaskByTaskId(@PathVariable long taskId) {
        return taskService.getTaskeByTaskId(taskId);
    }

}
