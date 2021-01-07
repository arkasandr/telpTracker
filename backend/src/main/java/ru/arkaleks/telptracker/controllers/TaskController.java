package ru.arkaleks.telptracker.controllers;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.arkaleks.telptracker.controllers.dto.EmployeeDto;
import ru.arkaleks.telptracker.controllers.dto.TaskDto;
import ru.arkaleks.telptracker.controllers.dto.TaskListDto;
import ru.arkaleks.telptracker.controllers.impl.TaskService;
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


    @PostMapping("/api/tasks/admin/add")
    TaskDto addNewTaskToEmployee(@RequestBody Task task) {
        return taskService.addNewTaskToEmployee(task);
    }


    @PostMapping("/api/tasks/admin/{taskId}/update")
    TaskDto updateCurrentTask(@RequestBody Task task, @PathVariable long taskId) {
        return taskService.updateCurrentTask(task, taskId);
    }


    @GetMapping("api/tasks/getall")
    List<TaskListDto> getAllTasks() {
        return taskService.getAllEmployeeTasks();
    }

    @PostMapping("api/tasks/searchall/{text}")
    List<TaskListDto> getSearchingTasksByCriteria(@PathVariable String text) {
        return taskService.getSearchingTasks(text);
    }


    @DeleteMapping("api/tasks/{taskId}/delete")
    void deleteTask(@PathVariable long taskId) {
        taskService.deleteTask(taskId);
    }


    @PostMapping("/api/tasks/status/{taskId}/start")
    void setTaskStatusToStart (@PathVariable long taskId) {
        taskService.setTaskStatusToStart(taskId);
    }


    @PostMapping("/api/tasks/status/{taskId}/pause")
    void setTaskStatusToPause (@PathVariable long taskId) {
        taskService.setTaskStatusToPause(taskId);
    }


    @PostMapping("/api/tasks/status/{taskId}/end")
    void setTaskStatusToEnd (@PathVariable long taskId) {
        taskService.setTaskStatusToEnd(taskId);
    }


    @PostMapping("api/tasks/members/{taskId}")
    Set<EmployeeDto> getTaskMembers(@PathVariable long taskId) {
        return taskService.getAllEmployeeByTaskId(taskId);
    }


    @PostMapping("api/tasks/current/{taskId}")
    TaskDto getTaskByTaskId(@PathVariable long taskId) {
        return taskService.getTaskByTaskId(taskId);
    }

}
