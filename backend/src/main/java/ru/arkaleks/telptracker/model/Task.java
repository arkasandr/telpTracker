package ru.arkaleks.telptracker.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TASKS")
public class Task {

    public Task(String title, Status taskStatus, String description, LocalDate startDate, LocalDate finishDate) {
        this.title = title;
        this.status = taskStatus;
        this.description = description;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public Task(Status status, String description, LocalDate startDate, LocalDate finishDate, Set<Employee> members) {
        this.status = status;
        this.description = description;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.members = members;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TASK_ID")
    private long taskId;

    private String title;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Lob
    private String description;

    private LocalDate startDate;

    private LocalDate finishDate;

//    @ManyToMany
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "employee_task",
            joinColumns = { @JoinColumn(name = "task_id") },
            inverseJoinColumns = { @JoinColumn(name = "employee_id") })
    @ElementCollection
    private Set<Employee> members;

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", status=" + status +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", members=" + members +
                '}';
    }
}
