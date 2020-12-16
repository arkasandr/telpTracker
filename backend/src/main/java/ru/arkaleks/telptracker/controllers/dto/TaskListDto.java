package ru.arkaleks.telptracker.controllers.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.arkaleks.telptracker.model.Status;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TaskListDto {

    private long taskId;

    private String title;

    private Status status;

    private String description;

    private LocalDate startDate;

    private LocalDate finishDate;

    private String[] members;
}
