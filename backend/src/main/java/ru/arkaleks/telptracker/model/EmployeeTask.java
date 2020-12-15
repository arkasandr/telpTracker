package ru.arkaleks.telptracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
//@Table(name = "EMPLOYEE_TASK")
public class EmployeeTask {


//    public EmployeeTask(long employeeId, long taskId) {
//        this.employeeId = employeeId;
//        this.taskId = taskId;
//    }
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "employee_id")
//    private long employeeId;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "task_id")
//    private long taskId;
}
