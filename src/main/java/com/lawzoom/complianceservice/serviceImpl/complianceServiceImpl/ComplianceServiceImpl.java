package com.lawzoom.complianceservice.serviceImpl.complianceServiceImpl;

import com.lawzoom.complianceservice.dto.complianceDto.ComplianceRequest;
import com.lawzoom.complianceservice.dto.complianceDto.ComplianceResponse;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.repository.ComplianceRepo;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.service.complianceService.ComplianceService;
import com.lawzoom.complianceservice.utility.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ComplianceServiceImpl implements ComplianceService {

    @Autowired
    private ResponseMapper responseMapper;

    @Autowired
    private ComplianceRepo complianceRepo;


    private ComplianceResponse mapToComplianceResponse(Compliance compliance) {
        return this.responseMapper.mapToComplianceResponse(compliance);
    }

    @Override
    public ResponseEntity deleteBusinessCompliance(Long complianceId, Long companyId) {


        if (!complianceRepo.existsById(complianceId)) {

            return ResponseEntity.notFound();
        }

        Compliance compliance = complianceRepo.findById(complianceId).orElse(null);
        if (compliance != null) {

            compliance.setEnable(false);
            complianceRepo.save(compliance);

            return  ResponseEntity.ok();
        } else {
            return ResponseEntity.notFound();
        }
    }

    @Override
    public ResponseEntity fetchAllComplianceByBusinessUnitId(Long businessUnitId) {
       List<Compliance> complianceList =this.complianceRepo.findAllByBusinessUnitId(businessUnitId);
        return null;
    }

    public ResponseEntity saveBusinessCompliance(ComplianceRequest complianceRequest, Long businessUnitId) {
        Compliance compliance = new Compliance();
        compliance.setTitle(complianceRequest.getTitle());
        compliance.setDescription(complianceRequest.getDescription());
        compliance.setApprovalState(complianceRequest.getApprovalState());
        compliance.setApplicableZone(complianceRequest.getApplicableZone());
        compliance.setStartDate(complianceRequest.getStartDate());
        compliance.setDueDate(complianceRequest.getDueDate());
        compliance.setCompletedDate(complianceRequest.getCompletedDate());
        compliance.setDuration(complianceRequest.getDuration());
        compliance.setWorkStatus(complianceRequest.getWorkStatus());
        compliance.setPriority(complianceRequest.getPriority());
        compliance.setCompanyId(complianceRequest.getCompanyId());
        compliance.setBusinessUnitId(businessUnitId);

        Compliance savedCompliance = complianceRepo.save(compliance);

        return ResponseEntity.ok(savedCompliance);
    }
    @Override
    public ResponseEntity updateBusinessCompliance(ComplianceRequest complianceRequest, Long businessUnitId) {
        Compliance existingCompliance = complianceRepo.findById(complianceRequest.getId())
                .orElseThrow(() -> new EntityNotFoundException("Compliance not found with ID: " + complianceRequest.getId()));

        existingCompliance.setTitle(complianceRequest.getTitle());
        existingCompliance.setDescription(complianceRequest.getDescription());
        existingCompliance.setApprovalState(complianceRequest.getApprovalState());
        existingCompliance.setApplicableZone(complianceRequest.getApplicableZone());
        existingCompliance.setStartDate(complianceRequest.getStartDate());
        existingCompliance.setDueDate(complianceRequest.getDueDate());
        existingCompliance.setCompletedDate(complianceRequest.getCompletedDate());
        existingCompliance.setDuration(complianceRequest.getDuration());
        existingCompliance.setWorkStatus(complianceRequest.getWorkStatus());
        existingCompliance.setPriority(complianceRequest.getPriority());
        existingCompliance.setCompanyId(complianceRequest.getCompanyId());

        existingCompliance.setBusinessUnitId(businessUnitId);

        Compliance updatedCompliance = complianceRepo.save(existingCompliance);

        return ResponseEntity.ok(updatedCompliance);
    }

    @Override
    public ResponseEntity fetchBusinessCompliance(Long complianceId, Long businessUnitId) {
        Compliance compliance = complianceRepo.findByIdAndBusinessUnitId(complianceId, businessUnitId);

        if (compliance != null) {
            return ResponseEntity.ok(compliance);
        }
        return ResponseEntity.notFound();
    }

    @Override
    public void saveAllCompliances(List<Compliance> complianceList) {

    }

    @Override
    public ResponseEntity fetchAllCompliances(Long companyId) {
        return null;
    }

    @Override
    public ResponseEntity saveCompliance(ComplianceRequest complianceRequest, Long companyId) {
        return null;
    }

    @Override
    public ResponseEntity updateCompliance(ComplianceRequest complianceRequest, Long companyId) {
        return null;
    }

    @Override
    public ResponseEntity fetchCompliance(Long complianceId, Long companyId) {
        return null;
    }

    @Override
    public ResponseEntity deleteCompliance(Long complianceId, Long companyId) {
        return null;
    }

    @Override
    public ResponseEntity updateComplianceStatus(Long complianceId, int status) {
        return null;
    }

    @Override
    public ResponseEntity fetchManageCompliancesByUserId(Long userId) {
        return null;
    }
}
