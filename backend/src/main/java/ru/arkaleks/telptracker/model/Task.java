package ru.arkaleks.telptracker.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TASKS")
public class Task {

    public Task(String title, Status taskStatus, String description, LocalDate startDate, LocalDate finishDate, LocalDate statusUpdateDate) {
        this.title = title;
        this.status = taskStatus;
        this.description = description;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.statusUpdateDate = statusUpdateDate;
    }

    public Task(LocalDate finishDate, Set<Employee> members) {
        this.finishDate = finishDate;
        this.members = members;
    }

    public Task(Status status, String description, LocalDate startDate, LocalDate finishDate, LocalDate statusUpdateDate, Set<Employee> members) {
        this.status = status;
        this.description = description;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.members = members;
        this.statusUpdateDate = statusUpdateDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TASK_ID")
    private long taskId;

    private String title;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    private LocalDate startDate;

    private LocalDate finishDate;

    private LocalDate statusUpdateDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "employee_task",
            joinColumns = { @JoinColumn(name = "task_id") },
            inverseJoinColumns = { @JoinColumn(name = "employee_id") })
    @ElementCollection
    private Set<Employee> members;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<TaskMessage> messages;


    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<TaskNotification> notificationList;

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", status=" + status +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", statusUpdateDate=" + statusUpdateDate +
                ", members=" + members +
                '}';
    }
}
