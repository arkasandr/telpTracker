package ru.arkaleks.telptracker.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NOTIFICATIONS")
public class TaskNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTIFICATION_ID")
    private long notificationId;

    private String text;

    private LocalDate createDate;

    private boolean isRead;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee author;

    @Override
    public String toString() {
        return "TaskNotification{" +
                "notificationId=" + notificationId +
                ", text='" + text + '\'' +
                ", isRead=" + isRead +
                '}';
    }
}
