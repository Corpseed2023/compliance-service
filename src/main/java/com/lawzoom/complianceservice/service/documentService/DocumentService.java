package com.lawzoom.complianceservice.service.documentService;

import com.lawzoom.complianceservice.dto.documentDto.DocumentRequest;
import com.lawzoom.complianceservice.response.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface DocumentService {
    ResponseEntity fetchAllComplianceDocument(Long complianceId);

    ResponseEntity saveComplianceDocument(DocumentRequest documentRequest, Optional<MultipartFile> file, Long complianceId);

    ResponseEntity updateComplianceDocument(DocumentRequest documentRequest, Optional<MultipartFile> file, Long complianceId);

    ResponseEntity fetchComplianceDocument(Long id, Long complianceId);

    ResponseEntity deleteComplianceDocument(Long id, Long complianceId);

    ResponseEntity fetchAllTaskDocument(Long taskId);

    ResponseEntity saveTaskDocument(DocumentRequest documentRequest, Optional<MultipartFile> file, Long taskId);

    ResponseEntity updateTaskDocument(DocumentRequest documentRequest, Optional<MultipartFile> file, Long taskId);

    ResponseEntity fetchTaskDocument(Long id, Long taskId);

    ResponseEntity deleteTaskDocument(Long id, Long taskId);

    ResponseEntity fetchAllSubTaskDocument(Long subTaskId);

    ResponseEntity saveSubTaskDocument(DocumentRequest documentRequest, Optional<MultipartFile> file, Long subTaskId);

    ResponseEntity updateSubTaskDocument(DocumentRequest documentRequest, Optional<MultipartFile> file, Long subTaskId);

    ResponseEntity fetchSubTaskDocument(Long id, Long subTaskId);

    ResponseEntity deleteSubTaskDocument(Long id, Long subTaskId);
}
