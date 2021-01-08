package ru.arkaleks.telptracker.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.arkaleks.telptracker.model.Task;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.Specification.where;

@RequiredArgsConstructor
@Component
public class CustomTaskRepository {

    private final TaskRepository taskRepository;


    private Specification<Task> titleLike(String title) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }

    private Specification<Task> textLike(String text) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get("description"), ("%" + text + "%"));
    }

    private Specification<Task> taskIdLike(String id) {
        if (id.matches(".*\\d.*")) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("taskId"), Long.valueOf(id));
        } else {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("taskId"), Long.valueOf("0"));
        }
    }

    public static Specification<Task> employeeSurnameLike(String surname) {
        return (Specification<Task>) (root, criteriaQuery, cb) -> {
            Join join = root.join("members");
            return cb.like(join.get("surname"), ("%" + surname + "%"));
        };
    }

    public static Specification<Task> allStartDatesIn(LocalDate[] period) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.between(root.get("startDate"), period[0], period[1]);
    }

    public static Specification<Task> allFinishDatesIn(LocalDate[] period) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.between(root.get("finishDate"), period[0], period[1]);
    }

    public List<Task> getSearchingTaskByCriteria(String text) {
        List<Task> searchingList = taskRepository.findAll(
                where(titleLike(text))
                        .or(textLike(text))
                        .or(taskIdLike(text))
                        .or(employeeSurnameLike(text)));
        return searchingList.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Task> getSearchingTaskByPeriod(LocalDate[] period) {
        List<Task> searchingList = taskRepository.findAll(allStartDatesIn(period)
        .and(allFinishDatesIn(period))
        );
        return searchingList.stream()
                .distinct()
                .collect(Collectors.toList());
    }
}
