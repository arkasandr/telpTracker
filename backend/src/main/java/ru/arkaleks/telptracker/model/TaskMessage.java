package ru.arkaleks.telptracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
@Table(name = "TASK_MESSAGES")
public class TaskMessage {

    public TaskMessage(String text, LocalTime spendTime, LocalDate spendDate, long taskNumber) {
        this.text = text;
        this.spendTime = spendTime;
        this.spendDate = spendDate;
        this.taskNumber = taskNumber;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MESSAGE_ID")
    private long messageId;

    @Lob
    private String text;

    private LocalTime spendTime;

    private LocalDate spendDate;

    private long taskNumber;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    private List<Enclosure> enclosureList;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee sender;

//    private List<Employee> recipients;

    @ManyToOne
    @JoinColumn(name = "TASK_ID")
    private Task task;


    @Override
    public String toString() {
        return "TaskMessage{" +
                "messageId=" + messageId +
                ", text='" + text + '\'' +
                ", spendTime=" + spendTime +
                ", spendDate=" + spendDate +
                ", enclosureList=" + enclosureList +
                ", sender=" + sender +
                ", task=" + task +
                '}';
    }
}
