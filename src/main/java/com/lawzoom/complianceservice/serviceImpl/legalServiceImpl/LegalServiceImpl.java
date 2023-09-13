package com.lawzoom.complianceservice.serviceImpl.legalServiceImpl;

import com.lawzoom.complianceservice.dao.complianceGuideDao.ComplianceGuideDao;
import com.lawzoom.complianceservice.dao.legalDao.LegalDao;
import com.lawzoom.complianceservice.dto.commonResponse.MessageResponse;
import com.lawzoom.complianceservice.dto.legalGuideDto.LegalGuideRequest;
import com.lawzoom.complianceservice.model.complianceGuide.ComplianceGuide;
import com.lawzoom.complianceservice.model.legalGuideModel.LegalGuide;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.service.legalService.LegalService;
import com.lawzoom.complianceservice.utility.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LegalServiceImpl implements LegalService {

    @Autowired
    private LegalDao legalDao;

    @Autowired
    private ComplianceGuideDao complianceGuideDao;

    @Autowired
    private ResponseMapper responseMapper;

    @Override
    public ResponseEntity fetchLegalGuides(Long guideId) {
        ComplianceGuide complianceGuide=this.complianceGuideDao.findComplianceGuideById(guideId);
        if(complianceGuide==null)
            return new ResponseEntity().badRequest("Compliance Guide Not Found !!");

        List<LegalGuide> legalGuideList=this.legalDao.findLegalGuidesByComplianceGuide(complianceGuide);
        if(legalGuideList.isEmpty())
            return new ResponseEntity().noContent();

        return new ResponseEntity().ok(legalGuideList.stream().map(l->this.responseMapper.mapToLegalGuideResponse(l)));
    }

    @Override
    public ResponseEntity saveLegalGuide(LegalGuideRequest legalGuideRequest, Long guideId) {
        ComplianceGuide complianceGuide=this.complianceGuideDao.findComplianceGuideById(guideId);
        if(complianceGuide==null)
            return new ResponseEntity().badRequest("Compliance Guide Not Found !!");

        LegalGuide findLegalGuide=this.legalDao.findLegalGuideByComplianceGuideAndTitle(complianceGuide,legalGuideRequest.getTitle());
        if(findLegalGuide!=null)
            return new ResponseEntity().badRequest("Legal Guide Already Exist !!");

        LegalGuide saveLegalGuide=this.legalDao.saveLegalDao(this.responseMapper.mapToSaveLegalGuide(legalGuideRequest,complianceGuide));
        if(saveLegalGuide==null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok(this.responseMapper.mapToLegalGuideResponse(saveLegalGuide));
    }

    @Override
    public ResponseEntity updateLegalGuide(LegalGuideRequest legalGuideRequest, Long guideId) {
        ComplianceGuide complianceGuide=this.complianceGuideDao.findComplianceGuideById(guideId);
        if(complianceGuide==null)
            return new ResponseEntity().badRequest("Compliance Guide Not Found !!");

        LegalGuide findLegalGuide=this.legalDao.findLegalGuideByComplianceGuideAndTitleAndIdNot(complianceGuide,legalGuideRequest.getTitle()
                ,legalGuideRequest.getId());
        if(findLegalGuide!=null)
            return new ResponseEntity().badRequest("Legal Guide Already Exist !!");

        LegalGuide updateLegalGuide=this.legalDao.updtaeLegalDao(this.responseMapper.mapToUpdateLegalGuide(legalGuideRequest,complianceGuide));
        if(updateLegalGuide==null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok(this.responseMapper.mapToLegalGuideResponse(updateLegalGuide));
    }

    @Override
    public ResponseEntity fetchLegalGuide(Long guideId, Long legalId) {
        ComplianceGuide complianceGuide=this.complianceGuideDao.findComplianceGuideById(guideId);
        if(complianceGuide==null)
            return new ResponseEntity().badRequest("Compliance Guide Not Found !!");

        LegalGuide legalGuide=this.legalDao.findLegalGuideByComplianceGuideAndId(complianceGuide,legalId);
        if(legalGuide==null)
            return new ResponseEntity().badRequest("Legal Guide Not Found !!");

        return new ResponseEntity().ok(this.responseMapper.mapToLegalGuideResponse(legalGuide));
    }

    @Override
    public ResponseEntity deleteLegalGuide(Long guideId, Long legalId) {
        ComplianceGuide complianceGuide=this.complianceGuideDao.findComplianceGuideById(guideId);
        if(complianceGuide==null)
            return new ResponseEntity().badRequest("Compliance Guide Not Found !!");

        LegalGuide legalGuide=this.legalDao.findLegalGuideByComplianceGuideAndId(complianceGuide,legalId);
        if(legalGuide==null)
            return new ResponseEntity().badRequest("Legal Guide Not Found !!");

        boolean deleteLegalGuide=this.legalDao.deleteLegalGuide(legalGuide);
        if(!deleteLegalGuide)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }
}
