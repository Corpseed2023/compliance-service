package com.lawzoom.complianceservice.service.complianceService;

import com.lawzoom.complianceservice.dto.complianceDto.ComplianceRequest;
import com.lawzoom.complianceservice.dto.complianceDto.ComplianceResponse;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.response.ResponseEntity;

import java.util.List;

public interface ComplianceService {

    ResponseEntity fetchAllComplianceByBusinessUnitId(Long businessUnitId);

    ComplianceResponse saveBusinessCompliance(ComplianceRequest complianceRequest, Long businessUnitId);

    ResponseEntity updateBusinessCompliance(ComplianceRequest complianceRequest, Long businessUnitId);

    ResponseEntity fetchBusinessCompliance(Long complianceId, Long businessUnitId);


    ResponseEntity deleteBusinessCompliance(Long complianceId, Long businessUnitId);
//==========================================complete till delete on 13 sept 2023==================================================

//======================================================================================?
    void saveAllCompliances(List<Compliance> complianceList);

    ResponseEntity fetchAllCompliances(Long companyId);

    ComplianceResponse saveCompliance(ComplianceRequest complianceRequest, Long companyId);

    ResponseEntity updateCompliance(ComplianceRequest complianceRequest, Long companyId);

    ResponseEntity fetchCompliance(Long complianceId, Long companyId);

    ResponseEntity deleteCompliance(Long complianceId, Long companyId);

//==========================================complete till delete on 14 sept 2023==================================================


    ResponseEntity updateComplianceStatus(Long complianceId, int status);

    ResponseEntity fetchManageCompliancesByUserId(Long userId);


}
