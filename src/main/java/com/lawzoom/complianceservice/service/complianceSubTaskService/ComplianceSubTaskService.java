package com.lawzoom.complianceservice.service.complianceSubTaskService;

import com.lawzoom.complianceservice.dto.complianceSubTaskDto.ComplianceSubTaskRequest;
import com.lawzoom.complianceservice.response.ResponseEntity;

public interface ComplianceSubTaskService {

    ResponseEntity fetchSubTaskByTask(Long taskId);

    ResponseEntity saveSubTask(ComplianceSubTaskRequest subTaskRequest, Long taskId);

    ResponseEntity updateSubTask(ComplianceSubTaskRequest subTaskRequest, Long taskId);

    ResponseEntity fetchSubTaskById(Long taskId, Long subTaskId);

    ResponseEntity deleteSubTaskById(Long taskId, Long subTaskId);
}
