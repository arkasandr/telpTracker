package ru.arkaleks.telptracker.controllers.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.arkaleks.telptracker.model.Employee;
import ru.arkaleks.telptracker.model.Status;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class TaskDto {

    public TaskDto(String title, Status taskStatus, String description, LocalDate startDate, LocalDate statusUpdateDate, LocalDate finishDate) {
        this.title = title;
        this.status = taskStatus;
        this.description = description;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.statusUpdateDate = statusUpdateDate;
    }

    public TaskDto(String title, Status taskStatus, String description, LocalDate startDate, LocalDate finishDate, LocalDate statusUpdateDate, Set<Employee> members) {
        this.title = title;
        this.status = taskStatus;
        this.description = description;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.statusUpdateDate = statusUpdateDate;
        this.members = members;
    }

    public TaskDto(long taskId, String title, Status status, String description, LocalDate startDate, LocalDate finishDate, LocalDate statusUpdateDate, Set<Employee> members) {
        this.taskId = taskId;
        this.title = title;
        this.status = status;
        this.description = description;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.statusUpdateDate = statusUpdateDate;
        this.members = members;
    }

    public TaskDto(long taskId, LocalDate finishDate, Set<Employee> members) {
        this.taskId = taskId;
        this.finishDate = finishDate;
        this.members = members;
    }

    public TaskDto(long taskId, LocalDate finishDate) {
        this.taskId = taskId;
        this.finishDate = finishDate;
    }

    private long taskId;

    private String title;

    private Status status;

    private String description;

    private LocalDate startDate;

    private LocalDate finishDate;

    private LocalDate statusUpdateDate;

    @JsonIgnore
    private Set<Employee> members;
}
