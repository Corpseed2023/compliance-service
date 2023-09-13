package com.lawzoom.complianceservice.service.complianceGuideService;

import com.lawzoom.complianceservice.dto.complianceGuideDto.ComplianceGuideRequest;
import com.lawzoom.complianceservice.response.ResponseEntity;

public interface ComplianceGuideService {

    ResponseEntity fetchGuidesByTask(Long taskId);

    ResponseEntity saveTaskGuide(ComplianceGuideRequest guideRequest, Long taskId);

    ResponseEntity updateTaskGuide(ComplianceGuideRequest guideRequest, Long taskId);

    ResponseEntity fetchTaskGuide(Long guideId, Long taskId);

    ResponseEntity deleteTaskGuide(Long guideId, Long taskId);

    ResponseEntity fetchGuidesBySubTask(Long subTaskId);

    ResponseEntity saveSubTaskGuide(ComplianceGuideRequest guideRequest, Long subTaskId);

    ResponseEntity updateSubTaskGuide(ComplianceGuideRequest guideRequest, Long subTaskId);

    ResponseEntity fetchSubTaskGuide(Long guideId, Long subTaskId);

    ResponseEntity deleteSubTaskGuide(Long guideId, Long subTaskId);
}
