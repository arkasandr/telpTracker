package ru.arkaleks.telptracker.controllers.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class TaskMessageDto {

    public TaskMessageDto(String text, LocalTime spendTime, LocalDate spendDate, long taskNumber) {
        this.text = text;
        this.spendTime = spendTime;
        this.spendDate = spendDate;
        this.taskNumber = taskNumber;
    }

    private long messageId;

    private String text;

    private LocalTime spendTime;

    private LocalDate spendDate;

    private long taskNumber;
}
