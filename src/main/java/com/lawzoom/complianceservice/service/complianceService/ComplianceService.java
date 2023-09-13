package com.lawzoom.complianceservice.service.complianceService;

import com.lawzoom.complianceservice.dto.complianceDto.ComplianceRequest;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.response.ResponseEntity;

import java.util.List;

public interface ComplianceService {

    void saveAllCompliances(List<Compliance> complianceList);

    ResponseEntity fetchAllCompliances(Long companyId);

    ResponseEntity saveCompliance(ComplianceRequest complianceRequest, Long companyId);

    ResponseEntity updateCompliance(ComplianceRequest complianceRequest, Long companyId);

    ResponseEntity fetchCompliance(Long complianceId, Long companyId);

    ResponseEntity deleteCompliance(Long complianceId, Long companyId);

    ResponseEntity fetchAllComplianceByBusinessUnitId(Long businessUnitId);

    ResponseEntity saveBusinessCompliance(ComplianceRequest complianceRequest, Long businessUnitId);

    ResponseEntity updateBusinessCompliance(ComplianceRequest complianceRequest, Long businessUnitId);

    ResponseEntity fetchBusinessCompliance(Long complianceId, Long businessUnitId);

    ResponseEntity deleteBusinessCompliance(Long complianceId, Long businessUnitId);

    ResponseEntity updateComplianceStatus(Long complianceId, int status);

    ResponseEntity fetchManageCompliancesByUserId(Long userId);
}
