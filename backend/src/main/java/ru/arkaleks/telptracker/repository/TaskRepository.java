package ru.arkaleks.telptracker.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.arkaleks.telptracker.model.Task;

@Repository
@Transactional
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    void deleteTaskByTaskId(long taskId);

    Task findTaskByTaskId(long taskId);

}
