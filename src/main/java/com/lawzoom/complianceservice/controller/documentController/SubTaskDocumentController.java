package com.lawzoom.complianceservice.controller.documentController;

import com.lawzoom.complianceservice.dto.documentDto.DocumentRequest;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.service.documentService.DocumentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/compliance/sub-task/document/{subTaskId}")
public class SubTaskDocumentController {

	private DocumentService documentService;

	public SubTaskDocumentController(DocumentService documentService) {
		this.documentService = documentService;
	}

	@GetMapping()
	public ResponseEntity fetchAllDocument(@PathVariable("subTaskId") Long subTaskId){
		return this.documentService.fetchAllSubTaskDocument(subTaskId);
	}
	
	@PostMapping("/save")
	public ResponseEntity saveDocument(@Valid @RequestPart DocumentRequest documentRequest,
			@RequestParam("file") Optional<MultipartFile> file,@PathVariable("subTaskId") Long subTaskId){
		
		return this.documentService.saveSubTaskDocument(documentRequest,file,subTaskId);
	}
	
	@PutMapping("/update")
	public ResponseEntity updateDocument(@Valid @RequestPart DocumentRequest documentRequest,
			@RequestParam("file") Optional<MultipartFile> file,@PathVariable("subTaskId") Long subTaskId){
		return this.documentService.updateSubTaskDocument(documentRequest,file,subTaskId);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity fetchDocument(@PathVariable("id") Long id,@PathVariable("subTaskId") Long subTaskId){
		return this.documentService.fetchSubTaskDocument(id,subTaskId);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity deleteDocument(@PathVariable("id") Long id,@PathVariable("subTaskId") Long subTaskId){
		return this.documentService.deleteSubTaskDocument(id,subTaskId);
	}
}
