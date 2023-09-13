package com.lawzoom.complianceservice.controller.complianceMapController;

import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.service.complianceMapService.ComplianceMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compliance/set-compliance-map")
public class SetComplianceMapController {

    @Autowired
    private ComplianceMapService complianceMapService;

    @GetMapping("/{userid}")
    public ResponseEntity setComplianceMap(@PathVariable("userid") Long userId){
        return this.complianceMapService.fetchAllComplianceMap(userId);
    }

    @GetMapping("/customize/{companyId}/{businessId}")
    private ResponseEntity setComplianceMapCustom(@PathVariable("companyId") Long companyId,
                                                  @PathVariable("businessId") Long businessId){
        return this.complianceMapService.fetchComplianceCustomize(companyId,businessId);
    }

}
