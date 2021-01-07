package ru.arkaleks.telptracker.controllers.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.arkaleks.telptracker.controllers.dto.EmployeeDto;
import ru.arkaleks.telptracker.controllers.dto.TaskDto;
import ru.arkaleks.telptracker.controllers.dto.TaskListDto;
import ru.arkaleks.telptracker.controllers.mapper.TaskMapper;
import ru.arkaleks.telptracker.model.*;
import ru.arkaleks.telptracker.repository.CustomTaskRepository;
import ru.arkaleks.telptracker.repository.EmployeeRepository;
import ru.arkaleks.telptracker.repository.TaskRepository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    private final CustomTaskRepository customTaskRepository;

    private final EmployeeRepository employeeRepository;

    private CurrentUserService currentUserService;

    private final TaskMapper taskMapper;

    private final TaskMessageService taskMessageService;


    @Transactional
    public TaskDto addNewTaskToEmployee(Task task) {
        log.info("Будет создана задача: " + task.toString());
        Employee manager = getLoginEmployee();
        Set<Task> employeeTasks = manager.getTasks();
        Set<Employee> employeeSet = task.getMembers();
        Employee[] array = employeeSet.toArray(new Employee[employeeSet.size()]);
        Employee executor = array[0];
        executor.getEmployeeRole().get(0).setEmployee(executor);
        log.info("Executor: " + executor);
        log.info("Executor roleName: " + executor.getEmployeeRole().get(0));
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
        log.info("Executor roleName_again: " + executor.getEmployeeRole().get(0));
        log.info("Новая задача: " + task.toString() + " создана успешно");
        return taskMapper.mapToTaskDto(task);
    }


    @Transactional
    public TaskDto updateCurrentTask(Task task, long taskId) {
        List<String> textList = new ArrayList<>();
        Employee manager = getLoginEmployee();
        Task currentTask = taskRepository.findTaskByTaskId(taskId);
        Set<Employee> employeeSet = task.getMembers();
        log.info("Будет обновлена задача под номером #" + taskId + " : " + currentTask.toString());
        if (task.getFinishDate() != null) {
            currentTask.setFinishDate(task.getFinishDate());
            String text = "Обновлено поле \"Дедлайн\". Новое значение: " + task.getFinishDate() + ". ";
            textList.add(text);
        }
        if (employeeSet.size() != 0) {
            Employee[] array = employeeSet.toArray(new Employee[employeeSet.size()]);
            Employee executor = array[0];
            log.info("executor is " + executor);
            if (executor != null) {
                executor.setRole(WorkRole.ИСПОЛНИТЕЛЬ);
                manager.setRole(WorkRole.ПОСТАНОВЩИК);
                employeeSet.add(executor);
                employeeSet.add(manager);
                currentTask.setMembers(employeeSet);
                String text = "Обновлено поле \"Исполнитель\". Новое значение: " + executor.getSurname() + " "
                        + executor.getFirstName() + " " + executor.getMiddleName() + ". ";
                textList.add(text);
            }
        }
        taskRepository.save(currentTask);
        String messText = String.join(", ", textList);
        TaskMessage message = new TaskMessage(messText, LocalDate.now(), taskId, LocalTime.now());
        taskMessageService.addNewMessageToTask(message);
        log.info("Задача успешно обновлена: " + currentTask.toString());
        return taskMapper.mapToTaskDto(task);
    }


    @Transactional
    public List<TaskListDto> getAllEmployeeTasks() {
        Employee employee = getLoginEmployee();
        Set<Task> employeeTasks = employee.getTasks();
        List<Task> resultTasks = employeeTasks
                .stream()
                .sorted(Comparator.comparing(Task::getTaskId))
                .collect(Collectors.toList());
        return convertToTaskListDto(resultTasks);
    }

    @Transactional
    public List<TaskListDto> getSearchingTasks(String text) {
        List<Task> taskList= customTaskRepository.getSearchingTask(text);
        return convertToTaskListDto(taskList);
    }



    @Transactional
    public void deleteTask(long taskId) {
        taskRepository.deleteTaskByTaskId(taskId);
        log.info("Задача под номером taskId = " + taskId + " удалена");
    }


    @Transactional
    public void setTaskStatusToStart(long taskId) {
        Task task = taskRepository.findTaskByTaskId(taskId);
        task.setStatus(Status.В_РАБОТЕ);
        task.setStatusUpdateDate(LocalDate.now());
        String messText = "Обновлен статус задачи: \"В РАБОТЕ\".";
        TaskMessage message = new TaskMessage(messText, LocalDate.now(), taskId, LocalTime.now());
        taskMessageService.addNewMessageToTask(message);
        taskRepository.save(task);
        log.info("Задача taskId = " + taskId + " принята в работу");
    }


    @Transactional
    public void setTaskStatusToPause(long taskId) {
        Task task = taskRepository.findTaskByTaskId(taskId);
        task.setStatus(Status.ПРИОСТАНОВЛЕНА);
        task.setStatusUpdateDate(LocalDate.now());
        String messText = "Обновлен статус задачи: \"ПРИОСТАНОВЛЕНА\".";
        TaskMessage message = new TaskMessage(messText, LocalDate.now(), taskId, LocalTime.now());
        taskMessageService.addNewMessageToTask(message);
        taskRepository.save(task);
        log.info("Задача taskId = " + taskId + " приостановлена");
    }


    @Transactional
    public void setTaskStatusToEnd(long taskId) {
        Task task = taskRepository.findTaskByTaskId(taskId);
        task.setStatus(Status.ЗАКОНЧЕНА);
        task.setStatusUpdateDate(LocalDate.now());
        String messText = "Обновлен статус задачи: \"ЗАКОНЧЕНА\".";
        TaskMessage message = new TaskMessage(messText, LocalDate.now(), taskId, LocalTime.now());
        taskMessageService.addNewMessageToTask(message);
        taskRepository.save(task);
        log.info("Задача taskId = " + taskId + " закончена");
    }

    private Employee getLoginEmployee() {
        String emplName = currentUserService.getCurrentEmployee().getUsername();
        Optional<Employee> optionalEmployee = employeeRepository.findByUsername(emplName);
        return optionalEmployee.orElseGet(Employee::new);
    }


    @Transactional
    public Set<EmployeeDto> getAllEmployeeByTaskId(long taskId) {
        Task task = taskRepository.findTaskByTaskId(taskId);
        Set<Employee> result = new HashSet<>(task.getMembers());
        log.info("Задача taskId = " + taskId + " имеет " + result.size() + " участника");
        return taskMapper.mapToSetEmployeeDto(result);
    }


    @Transactional
    public TaskDto getTaskByTaskId(long taskId) {
        Task task = taskRepository.findTaskByTaskId(taskId);
        log.info("Задача taskId = " + taskId + " найдена успешно");
        return taskMapper.mapToTaskDto(task);
    }


    private List<TaskListDto> convertToTaskListDto(List<Task> taskList) {
        List<TaskListDto> result = new ArrayList<>();
        for (Task oldTask : taskList) {
            TaskListDto task = new TaskListDto();
            task.setTaskId(oldTask.getTaskId());
            task.setTitle(oldTask.getTitle());
            task.setDescription(oldTask.getDescription());
            task.setStartDate(oldTask.getStartDate());
            task.setFinishDate(oldTask.getFinishDate());
            task.setStatus((oldTask.getStatus()));
            task.setStatusUpdateDate((oldTask.getStatusUpdateDate()));
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
}
