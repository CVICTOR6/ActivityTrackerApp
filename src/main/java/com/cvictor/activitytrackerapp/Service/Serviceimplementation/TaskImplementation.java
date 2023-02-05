package com.cvictor.activitytrackerapp.Service.Serviceimplementation;


import com.cvictor.activitytrackerapp.DTO.TaskDTO;
import com.cvictor.activitytrackerapp.DTO.UserDTO;
import com.cvictor.activitytrackerapp.Enums.Status;
import com.cvictor.activitytrackerapp.Model.Task;
import com.cvictor.activitytrackerapp.Model.User;
import com.cvictor.activitytrackerapp.Repository.TaskRepository;
import com.cvictor.activitytrackerapp.Repository.UserRepository;
import com.cvictor.activitytrackerapp.Service.TaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class TaskImplementation implements TaskService {
    private TaskRepository taskRepository;
    private UserRepository userRepository;

    @Override
    public Long createTask(UserDTO userDTO, TaskDTO taskDTO) {

        Task task = new Task();
        Optional<User> taskUser = userRepository.findById(userDTO.getId());

        if (taskUser.isPresent()){
            User  user = taskUser.get();

            task.setTitle(taskDTO.getTitle());
            task.setStatus(Status.PENDING);
            task.setDescription(taskDTO.getDescription());
            task.setUser(user);
            task.setUpdatedAt(null);
            taskRepository.save(task);

            return task.getTaskId();

        }
        else{ return null; }

    }

    @Override
    public Long updateTask(UserDTO userDTO, TaskDTO taskDto) {
        Task task = taskRepository.findTaskByTaskIdAndUserId(taskDto.getTaskId(), userDTO.getId()).orElse(null);
        if (task == null){return null;}

        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setTitle(taskDto.getTitle());
        if (taskDto.getStatus().equals(Status.COMPLETED.toString()) ){

            task.setCompletedAt(LocalDateTime.now());
            task.setStatus(Status.COMPLETED);
        }
        if (taskDto.getStatus().equals("IN PROGRESS") ){
            task.setStatus(Status.IN_PROGRESS);
        }
        task.setUpdatedAt(LocalDateTime.now());
        taskRepository.save(task);
        return task.getTaskId();
    }

    @Override
    public TaskDTO getTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId).get();
        return convertToTaskDTO(task);
    }


    @Override
    public List<TaskDTO> getTasksByStatus(Status status, UserDTO userDTO) {

        List<Task> tasks = taskRepository.findAllByUserIdAndStatus(userDTO.getId(),status);

        // create a list of taskdtos that is empty
        List <TaskDTO> taskDTOS = new ArrayList<TaskDTO>();

        //loop thru to convert each task to task dto
        for (Task task : tasks){
            TaskDTO taskDTO = convertToTaskDTO(task);
            taskDTOS.add(taskDTO);
        }
        return taskDTOS;
    }

    @Override
    public List<TaskDTO> getAllTasksOfUser(UserDTO userDTO) {
// get all tasks of user
        List<Task> tasks = taskRepository.findAllByUserId(userDTO.getId());

        // create a list of taskdtos that is empty
        List <TaskDTO> taskDTOS = new ArrayList<TaskDTO>();

        //loop thru to convert each task to task dto
          for (Task task : tasks){
              TaskDTO taskDTO = convertToTaskDTO(task);
              taskDTOS.add(taskDTO);
          }
        return taskDTOS;
    }

    @Override
    public Boolean deleteTaskById(Long taskId, UserDTO userDTO) {
        // find if such task exists in db
        Task task = taskRepository.findTaskByTaskIdAndUserId(taskId, userDTO.getId()).orElse(null);
        if (task ==null){return false;}

        // if it is there, delete it
        else {
            taskRepository.deleteById(taskId);
            return true;
        }
    }


    private static TaskDTO convertToTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setStatus(task.getStatus().toString());
        taskDTO.setTaskId(task.getTaskId());
        taskDTO.setCompletedAt(task.getCompletedAt());
        taskDTO.setCreatedAt(task.getCreatedAt());
        taskDTO.setUpdatedAt(task.getUpdatedAt());
        return taskDTO;
    }
}
