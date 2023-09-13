package com.lawzoom.complianceservice.service.taskActionService;

import com.lawzoom.complianceservice.dto.taskActionDto.TaskActionRequest;
import com.lawzoom.complianceservice.response.ResponseEntity;

public interface TaskActionService {
    ResponseEntity findTaskActionsByTask(Long taskId);

    ResponseEntity saveTaskAction(TaskActionRequest taskActionRequest, Long taskId);

    ResponseEntity updateTaskAction(TaskActionRequest taskActionRequest, Long taskId);

    ResponseEntity fetchTaskActionById(Long taskId, Long actionId);

    ResponseEntity deleteTaskActionById(Long taskId, Long actionId);

    ResponseEntity findTaskActionsBySubTask(Long subTaskId);

    ResponseEntity saveSubTaskAction(TaskActionRequest taskActionRequest, Long subTaskId);

    ResponseEntity updateSubTaskAction(TaskActionRequest taskActionRequest, Long subTaskId);

    ResponseEntity fetchSubTaskActionById(Long subTaskId, Long actionId);

    ResponseEntity deleteSubTaskActionById(Long subTaskId, Long actionId);
}
