package com.lawzoom.complianceservice.controller.complianceController;

import com.lawzoom.complianceservice.dto.complianceDto.ComplianceRequest;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.service.complianceService.ComplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/compliance/company/{companyId}")
public class CompanyComplianceController {

	@Autowired
	private ComplianceService complianceService;

	@GetMapping()
	public ResponseEntity fetchAllCompliances(@PathVariable("companyId") Long companyId){
		return this.complianceService.fetchAllCompliances(companyId);
	}
	
	@PostMapping("/save")
	public ResponseEntity saveCompliance(@Valid @RequestBody ComplianceRequest complianceRequest,@PathVariable("companyId") Long companyId){
		return this.complianceService.saveCompliance(complianceRequest,companyId);
	}
	
	@PutMapping("/update")
	public ResponseEntity updateCompliance(@Valid @RequestBody ComplianceRequest complianceRequest,@PathVariable("companyId") Long companyId){
		return this.complianceService.updateCompliance(complianceRequest,companyId);
	}
	
	@GetMapping("/{complianceId}")
	public ResponseEntity fetchCompliance(@PathVariable("id") Long complianceId,@PathVariable("companyId") Long companyId){
		return this.complianceService.fetchCompliance(complianceId,companyId);
	}
	
	@DeleteMapping("/{complianceId}")
	public ResponseEntity deleteCompliance(@PathVariable("complianceId") Long complianceId,@PathVariable("companyId") Long companyId){
		return this.complianceService.deleteCompliance(complianceId,companyId);
	}

}
