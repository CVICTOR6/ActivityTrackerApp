package com.cvictor.activitytrackerapp.Repository;


import com.cvictor.activitytrackerapp.DTO.TaskDTO;
import com.cvictor.activitytrackerapp.Enums.Status;
import com.cvictor.activitytrackerapp.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByUserIdAndStatus(Long id, Status status);

    List<Task> findAllByUserId(Long id);

    Optional<Task> findTaskByTaskIdAndUserId(Long taskId, Long userId);
}
