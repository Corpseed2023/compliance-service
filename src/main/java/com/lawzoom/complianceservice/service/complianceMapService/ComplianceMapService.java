package com.lawzoom.complianceservice.service.complianceMapService;


import com.lawzoom.complianceservice.response.ResponseEntity;

public interface ComplianceMapService {

    ResponseEntity fetchAllComplianceMap(Long userId);

    ResponseEntity fetchComplianceCustomize(Long companyId, Long businessId);
}
