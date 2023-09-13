package com.lawzoom.complianceservice.controller.complianceController;

import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.service.complianceService.ComplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/compliance")
public class ComplianceController {

    @Autowired
    private ComplianceService complianceService;

    @GetMapping("/{userid}")
    public ResponseEntity fetchAllCompliance(@PathVariable("userid") Long userId){
        return this.complianceService.fetchManageCompliancesByUserId(userId);
    }

    @PutMapping("/status/{complianceId}/{status}")
    public ResponseEntity updateStatus(@PathVariable("complianceId") Long complianceId,@PathVariable("status") int status){
        return this.complianceService.updateComplianceStatus(complianceId,status);
    }
}
