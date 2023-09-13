package com.lawzoom.complianceservice.controller.complianceMapController;

import com.lawzoom.complianceservice.dto.businessUnitDto.BusinessUnitResponse;
import com.lawzoom.complianceservice.dto.companyResponseDto.CompanyResponse;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.service.complianceMapService.InternalComplianceMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compliance")
public class InternalComplianceMapController {

    @Autowired
    private InternalComplianceMapService internalComplianceMapService;

    @PostMapping("/map/company")
    public ResponseEntity<?> mapCompanyCompliances(@RequestBody CompanyResponse companyResponse){
        System.out.println("starting mapping compliances..............");

        return this.internalComplianceMapService.mapCompanyCompliances(companyResponse);
    }

    @PostMapping("/map/business")
    public ResponseEntity<?> mapBusinessCompliances(@RequestBody BusinessUnitResponse businessUnitResponse){
        System.out.println("starting mapping compliances of business unit..............");
        return this.internalComplianceMapService.mapBusinessUnitCompliances(businessUnitResponse);
    }
}
