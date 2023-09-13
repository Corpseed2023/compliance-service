package com.lawzoom.complianceservice.controller.documentController;

import com.lawzoom.complianceservice.dto.documentDto.DocumentRequest;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.service.documentService.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/compliance/document/{complianceId}")
public class ComplianceDocumentController {

	@Autowired
	private DocumentService documentService;

	@GetMapping()
	public ResponseEntity fetchAllDocument(@PathVariable("complianceId") Long complianceId){
		return this.documentService.fetchAllComplianceDocument(complianceId);
	}
	
	@PostMapping("/save")
	public ResponseEntity saveDocument(@Valid @RequestPart DocumentRequest documentRequest,
			@RequestParam("file") Optional<MultipartFile> file
			,@PathVariable("complianceId") Long complianceId){
		return this.documentService.saveComplianceDocument(documentRequest,file,complianceId);
	}

	@PutMapping("/update")
	public ResponseEntity updateDocument(@Valid @RequestPart DocumentRequest documentRequest,
			@RequestParam("file") Optional<MultipartFile> file,@PathVariable("complianceId") Long complianceId){
		return this.documentService.updateComplianceDocument(documentRequest,file,complianceId);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity fetchDocument(@PathVariable("id") Long id,@PathVariable("complianceId") Long complianceId){
		return this.documentService.fetchComplianceDocument(id,complianceId);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity deleteDocument(@PathVariable("id") Long id,@PathVariable("complianceId") Long complianceId){
		return this.documentService.deleteComplianceDocument(id,complianceId);
	}
}
