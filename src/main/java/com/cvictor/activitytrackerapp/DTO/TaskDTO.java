package com.cvictor.activitytrackerapp.DTO;

import com.cvictor.activitytrackerapp.Enums.Status;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class TaskDTO {
    private Long taskId;
    private String title;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;
}
