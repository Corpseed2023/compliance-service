package com.lawzoom.complianceservice.serviceImpl.complianceServiceImpl;

import com.lawzoom.complianceservice.dao.complianceDao.ComplianceDao;
import com.lawzoom.complianceservice.dto.complianceDto.ComplianceRequest;
import com.lawzoom.complianceservice.dto.complianceDto.ComplianceResponse;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.service.complianceService.ComplianceService;
import com.lawzoom.complianceservice.utility.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComplianceServiceImpl implements ComplianceService {

    @Autowired
    private ComplianceDao complianceDao;

    @Autowired
    private ResponseMapper responseMapper;

   @Override
    public ResponseEntity fetchAllCompliances(Long companyId) {

       List<Compliance> complianceList=this.complianceDao.fetchAllComplianceByCompanyId(companyId);
        if(complianceList.isEmpty())
            return new ResponseEntity().noContent();

        return new ResponseEntity().ok(complianceList.stream().map(this::mapToComplianceResponse)
                .collect(Collectors.toList()));
    }

    private ComplianceResponse mapToComplianceResponse(Compliance compliance) {
        return this.responseMapper.mapToComplianceResponse(compliance);
    }

    @Override
    public ResponseEntity saveCompliance(ComplianceRequest complianceRequest, Long companyId) {

       Compliance saveCompliance=this.complianceDao.saveCompliance(
               this.responseMapper.mapToSaveCompliance(complianceRequest,companyId,0L));

        if(saveCompliance==null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity updateCompliance(ComplianceRequest complianceRequest, Long companyId) {

       Compliance updateCompliance=this.complianceDao.updateCompliance(
               this.responseMapper.mapToUpdateCompliance(complianceRequest,companyId,0L));

        if(updateCompliance==null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity fetchCompliance(Long complianceId, Long companyId) {

       Compliance compliance=this.complianceDao.findComplianceByCompanyAndId(companyId,complianceId);
        if(compliance==null)
            return new ResponseEntity().badRequest("Compliance Not Found !!");

        return new ResponseEntity().ok(mapToComplianceResponse(compliance));
    }

    @Override
    public ResponseEntity deleteCompliance(Long complianceId, Long companyId) {

       Compliance compliance=this.complianceDao.findComplianceByCompanyAndId(companyId,complianceId);
        if(compliance==null)
            return new ResponseEntity().badRequest("Compliance Not Found !!");

        boolean deleteCompliance=this.complianceDao.deleteCompliance(compliance);

        if(!deleteCompliance)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }



    @Override
    public ResponseEntity fetchAllComplianceByBusinessUnitId(Long businessUnitId) {
        List<Compliance> complianceList = this.complianceDao.findCompliancesByBusinessUnitId(businessUnitId);
        return new ResponseEntity().ok(complianceList.stream().map(this::mapToComplianceResponse)
        .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity saveBusinessCompliance(ComplianceRequest complianceRequest, Long businessUnitId) {

       Compliance saveCompliance=this.complianceDao.saveCompliance(
               this.responseMapper.mapToSaveCompliance(complianceRequest,0L,businessUnitId));

        if(saveCompliance==null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity updateBusinessCompliance(ComplianceRequest complianceRequest, Long businessUnitId) {

       Compliance updateCompliance=this.complianceDao.updateCompliance(
               this.responseMapper.mapToUpdateCompliance(complianceRequest,0L,businessUnitId));

        if(updateCompliance==null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity fetchBusinessCompliance(Long complianceId, Long businessUnitId) {

       Compliance compliance=this.complianceDao.findComplianceByBusinessUnitAndId(businessUnitId,complianceId);
        if(compliance==null)
            return new ResponseEntity().badRequest("Compliance Not Found !!");

        return new ResponseEntity().ok(mapToComplianceResponse(compliance));
    }

    @Override
    public ResponseEntity deleteBusinessCompliance(Long complianceId, Long businessUnitId) {

       Compliance compliance=this.complianceDao.findComplianceByBusinessUnitAndId(businessUnitId,complianceId);
        if(compliance==null)
            return new ResponseEntity().badRequest("Compliance Not Found !!");

        boolean deleteCompliance=this.complianceDao.deleteCompliance(compliance);

        if(!deleteCompliance)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity updateComplianceStatus(Long complianceId, int status) {
        Compliance compliance = this.complianceDao.fetchComplianceById(complianceId);
        if(compliance==null)return new ResponseEntity().badRequest("Something Went-Wrong, Please Try-again later !!");

        compliance.setWorkStatus(status);
        this.complianceDao.updateCompliance(compliance);

        return new ResponseEntity().ok("Successfully updated !!");
    }

    @Override
    public ResponseEntity fetchManageCompliancesByUserId(Long userId) {
        return new ResponseEntity().ok(this.complianceDao.fetchManageCompliancesByUserId(userId));
    }

    @Override
    public void saveAllCompliances(List<Compliance> complianceList) {
        this.complianceDao.saveAllCompliances(complianceList);
    }

}
