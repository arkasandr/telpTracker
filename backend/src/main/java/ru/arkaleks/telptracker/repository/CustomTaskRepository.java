package ru.arkaleks.telptracker.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.arkaleks.telptracker.model.Task;

import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

@RequiredArgsConstructor
@Component
public class CustomTaskRepository {

    private final TaskRepository taskRepository;


    private Specification<Task> titleLike(String title){
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get("title"), "%"+title+"%");
    }

    private Specification<Task> textLike(String text){
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get("description"), ("%"+text+"%"));
    }

    private Specification<Task> taskIdLike(String id){
        if(id.matches(".*\\d.*")) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("taskId"), Long.valueOf(id));
        } else {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("taskId"), Long.valueOf("0"));
        }
    }

    public List<Task> getSearchingTask(String text) {
        return taskRepository.findAll(
                where(titleLike(text))
                        .or(textLike(text))
                        .or(taskIdLike(text)));
    }
}
