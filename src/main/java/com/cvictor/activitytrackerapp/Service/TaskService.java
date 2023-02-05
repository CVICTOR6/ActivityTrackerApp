package com.cvictor.activitytrackerapp.Service;

import com.cvictor.activitytrackerapp.DTO.TaskDTO;
import com.cvictor.activitytrackerapp.DTO.UserDTO;
import com.cvictor.activitytrackerapp.Enums.Status;
import org.springframework.stereotype.Service;

import java.util.List;



public interface TaskService {

    Long createTask (UserDTO userDTO, TaskDTO taskDTO);

    Long updateTask (UserDTO userDTO, TaskDTO taskDto);

    TaskDTO getTaskById (Long taskId);

    List<TaskDTO> getTasksByStatus(Status status, UserDTO userDTO);

    List<TaskDTO> getAllTasksOfUser (UserDTO userDTO);

    Boolean deleteTaskById (Long taskId, UserDTO userDTO);
}
