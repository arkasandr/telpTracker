package ru.arkaleks.telptracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.arkaleks.telptracker.model.TaskMessage;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */

@Repository
@Transactional
public interface TaskMessageRepository extends JpaRepository<TaskMessage, Long> {

    void deleteTaskMessageByMessageId(long messageId);

    TaskMessage findTaskMessageByMessageId(long messageId);
}
