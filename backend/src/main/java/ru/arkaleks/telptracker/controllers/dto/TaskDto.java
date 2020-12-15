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

    public TaskDto(String title, Status taskStatus, String description, LocalDate startDate, LocalDate finishDate) {
        this.title = title;
        this.status = taskStatus;
        this.description = description;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public TaskDto(String title, Status taskStatus, String description, LocalDate startDate, LocalDate finishDate, Set<Employee> members) {
        this.title = title;
        this.status = taskStatus;
        this.description = description;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.members = members;
    }

    public TaskDto(long taskId, String title, Status status, String description, LocalDate startDate, LocalDate finishDate, Set<Employee> members) {
        this.taskId = taskId;
        this.title = title;
        this.status = status;
        this.description = description;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.members = members;
    }

    private long taskId;

    private String title;

    private Status status;

    private String description;

    private LocalDate startDate;

    private LocalDate finishDate;

    @JsonIgnore
    private Set<Employee> members;
}
