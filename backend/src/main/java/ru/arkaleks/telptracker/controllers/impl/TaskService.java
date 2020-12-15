package ru.arkaleks.telptracker.controllers.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.arkaleks.telptracker.controllers.dto.TaskDto;
import ru.arkaleks.telptracker.controllers.dto.TaskListDto;
import ru.arkaleks.telptracker.controllers.mapper.TaskMapper;
import ru.arkaleks.telptracker.model.Employee;
import ru.arkaleks.telptracker.model.Status;
import ru.arkaleks.telptracker.model.Task;
import ru.arkaleks.telptracker.model.WorkRole;
import ru.arkaleks.telptracker.repository.EmployeeRepository;
import ru.arkaleks.telptracker.repository.TaskRepository;


import java.util.*;
import java.util.stream.Collectors;


/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */

@Slf4j
@AllArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    private final EmployeeRepository employeeRepository;

    private CurrentUserService currentUserService;

    private final TaskMapper taskMapper;


    /**
     * Метод добавляет новую задачу
     */
    @Transactional
    public TaskDto addNewTaskToEmployee(Task task) {
        log.info("Будет создана задача: " + task.toString());
        String emplName = currentUserService.getCurrentEmployee().getUsername();
        Optional<Employee> optionalEmployee = employeeRepository.findByUsername(emplName);
        Employee manager = optionalEmployee.orElseGet(Employee::new);
        Set<Task> employeeTasks = manager.getTasks();
        Set<Employee> employeeSet = task.getMembers();
        Employee[] array = employeeSet.toArray(new Employee[employeeSet.size()]);
        Employee executor = array[0];
        executor.setRole(WorkRole.ИСПОЛНИТЕЛЬ);
        manager.setRole(WorkRole.ПОСТАНОВЩИК);
        task.getMembers().add(manager);
        task.getMembers().add(executor);
        task.setStatus(Status.СОЗДАНА);
        employeeTasks.add(task);
        manager.setTasks(employeeTasks);
        Set<Task> executorTasks = executor.getTasks();
        executorTasks.add(task);
        executor.setTasks(executorTasks);
        taskRepository.save(task);
        employeeRepository.save(manager);
        employeeRepository.save(executor);
        log.info("Новая задача: " + task.toString() + " создана успешно");
        return taskMapper.mapToTaskDto(task);
    }


    /**
     * Метод возвращает список всех задач
     */
//    @Transactional
//    public List<TaskDto> getAllEmployeeTasks() {
//        String emplName = currentUserService.getCurrentEmployee().getUsername();
//        Optional<Employee> optionalEmployee = employeeRepository.findByUsername(emplName);
//        Employee employee = optionalEmployee.orElseGet(Employee::new);
//        Set<Task> employeeTasks = employee.getTasks();
//        List<Task> result = employeeTasks
//                .stream()
//                .sorted(Comparator.comparing(Task::getTaskId))
//                .collect(Collectors.toList());
//        return taskMapper.mapToTaskDtoList(result);
//    }

    @Transactional
    public List<TaskListDto> getAllEmployeeTasks() {
        List<TaskListDto> result = new ArrayList<>();
        String emplName = currentUserService.getCurrentEmployee().getUsername();
        Optional<Employee> optionalEmployee = employeeRepository.findByUsername(emplName);
        Employee employee = optionalEmployee.orElseGet(Employee::new);
        Set<Task> employeeTasks = employee.getTasks();
        List<Task> resultTasks = employeeTasks
                .stream()
                .sorted(Comparator.comparing(Task::getTaskId))
                .collect(Collectors.toList());
        for(Task oldTask : resultTasks) {
            TaskListDto task = new TaskListDto();
            task.setTaskId(oldTask.getTaskId());
            task.setTitle(oldTask.getTitle());
            task.setDescription(oldTask.getDescription());
            task.setStartDate(oldTask.getStartDate());
            task.setFinishDate(oldTask.getFinishDate());
            task.setStatus((oldTask.getStatus()));
            List<String> surnames = oldTask.getMembers()
                    .stream()
                    .filter(x -> x.getRole().equals(WorkRole.ИСПОЛНИТЕЛЬ))
                    .map(x -> x.getSurname())
                    .collect(Collectors.toList());
            String[] array = surnames.stream().toArray(String[]::new);
            task.setMembers(array);
            result.add(task);
        }
        return result;
    }

    /**
     * Метод удаляет задачу
     */
    @Transactional
    public void deleteTask(long taskId) {
        String emplName = currentUserService.getCurrentEmployee().getUsername();
        Optional<Employee> optionalEmployee = employeeRepository.findByUsername(emplName);
        Employee employee = optionalEmployee.orElseGet(Employee::new);
        Set<Task> employeeTasks = employee.getTasks();
        Set<Task> newTasks = new HashSet<>();
        for(Task task : employeeTasks) {
            if(task.getTaskId() != taskId) {
                newTasks.add(task);
            }
        }
        employee.setTasks(newTasks);
        employeeRepository.save(employee);
        taskRepository.deleteTaskByTaskId(taskId);
        log.info("Задача под номером taskId = " + taskId + " успешно удалена");
    }
}