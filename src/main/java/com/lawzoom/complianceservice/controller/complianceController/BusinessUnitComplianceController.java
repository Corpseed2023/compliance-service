package com.lawzoom.complianceservice.controller.complianceController;

import com.lawzoom.complianceservice.dto.complianceDto.ComplianceRequest;
import com.lawzoom.complianceservice.dto.complianceDto.ComplianceResponse;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.service.complianceService.ComplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/compliance/business/{businessUnitId}")
public class BusinessUnitComplianceController {

    @Autowired
    private ComplianceService complianceService;

    @GetMapping()
    public ResponseEntity fetchAllCompliances(@PathVariable("businessUnitId") Long businessUnitId){
        return this.complianceService.fetchAllComplianceByBusinessUnitId(businessUnitId);
    }

    @PostMapping("/save")
    public ComplianceResponse saveCompliance(@Valid @RequestBody ComplianceRequest complianceRequest, @PathVariable("businessUnitId") Long businessUnitId){
        return this.complianceService.saveBusinessCompliance(complianceRequest,businessUnitId);
    }

    @PutMapping("/update")
    public ResponseEntity updateCompliance(@Valid @RequestBody ComplianceRequest complianceRequest,@PathVariable("businessUnitId") Long businessUnitId){
        return this.complianceService.updateBusinessCompliance(complianceRequest,businessUnitId);
    }

    @GetMapping("/{complianceId}")
    public ResponseEntity fetchCompliance(@PathVariable("complianceId") Long complianceId,@PathVariable("businessUnitId") Long businessUnitId){
        return this.complianceService.fetchBusinessCompliance(complianceId,businessUnitId);
    }

    @DeleteMapping("/{complianceId}")
    public ResponseEntity deleteCompliance(@PathVariable("complianceId") Long complianceId,@PathVariable("businessUnitId") Long businessUnitId){
        return this.complianceService.deleteBusinessCompliance(complianceId,businessUnitId);
    }

}
