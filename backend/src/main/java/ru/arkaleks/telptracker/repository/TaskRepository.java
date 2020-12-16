package ru.arkaleks.telptracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.arkaleks.telptracker.model.Task;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    void deleteTaskByTaskId(long taskId);

    Task findTaskByTaskId(long taskId);
}
