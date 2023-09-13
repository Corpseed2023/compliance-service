package com.lawzoom.complianceservice.serviceImpl.complianceMapServiceImpl;

import com.lawzoom.complianceservice.dao.complianceDao.ComplianceDao;
import com.lawzoom.complianceservice.dto.complianceDto.ComplianceResponse;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.service.complianceMapService.ComplianceMapService;
import com.lawzoom.complianceservice.utility.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComplianceMapServiceImpl implements ComplianceMapService {

    @Autowired
    private ComplianceDao complianceDao;

    @Autowired
    private ResponseMapper responseMapper;

    @Override
    public ResponseEntity fetchAllComplianceMap(Long userId) {
        return new ResponseEntity().ok(this.complianceDao.fetchAllSetComplianceMap(userId));
    }

    @Override
    public ResponseEntity fetchComplianceCustomize(Long companyId, Long businessId) {

        return new ResponseEntity().ok(this.complianceDao.fetchComplianceCustom(companyId,businessId)
                .stream().map(this::mapToComplianceResponse));
    }

    private ComplianceResponse mapToComplianceResponse(Compliance compliance) {
        return this.responseMapper.mapToComplianceResponse(compliance);
    }

}
