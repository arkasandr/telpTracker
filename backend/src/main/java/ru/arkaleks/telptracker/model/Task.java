package ru.arkaleks.telptracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TASKS")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TASK_ID")
    private long taskId;

    private String title;

    @Enumerated(EnumType.STRING)
    private Status taskStatus;

    @Lob
    private String description;

    private LocalDate startDate;

    private LocalDate finishDate;
//
//    @ManyToMany(mappedBy = "tasks")
//    private Set<Employee> membersList;

    @OneToMany(mappedBy = "taskId", cascade = CascadeType.ALL)
    private Set<EmployeeTask> membersList = new HashSet<>();
}
