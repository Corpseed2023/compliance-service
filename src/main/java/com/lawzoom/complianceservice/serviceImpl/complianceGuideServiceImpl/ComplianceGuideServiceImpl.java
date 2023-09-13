package com.lawzoom.complianceservice.serviceImpl.complianceGuideServiceImpl;

import com.lawzoom.complianceservice.dao.complianceGuideDao.ComplianceGuideDao;
import com.lawzoom.complianceservice.dao.complianceSubTaskDao.ComplianceSubTaskDao;
import com.lawzoom.complianceservice.dao.complianceTaskDao.ComplianceTaskDao;
import com.lawzoom.complianceservice.dto.complianceGuideDto.ComplianceGuideRequest;
import com.lawzoom.complianceservice.model.complianceGuide.ComplianceGuide;
import com.lawzoom.complianceservice.model.complianceSubTaskModel.ComplianceSubTask;
import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.service.complianceGuideService.ComplianceGuideService;
import com.lawzoom.complianceservice.utility.ResponseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplianceGuideServiceImpl implements ComplianceGuideService {

    private ComplianceGuideDao complianceGuideDao;

    private ComplianceTaskDao complianceTaskDao;

    private ComplianceSubTaskDao complianceSubTaskDao;

    private ResponseMapper responseMapper;

    public ComplianceGuideServiceImpl(ComplianceGuideDao complianceGuideDao,
                                      ComplianceTaskDao complianceTaskDao,
                                      ComplianceSubTaskDao complianceSubTaskDao,
                                      ResponseMapper responseMapper) {
        this.complianceGuideDao = complianceGuideDao;
        this.complianceTaskDao = complianceTaskDao;
        this.complianceSubTaskDao = complianceSubTaskDao;
        this.responseMapper = responseMapper;
    }

    @Override
    public ResponseEntity fetchGuidesByTask(Long taskId) {
        ComplianceTask complianceTask=this.complianceTaskDao.findComplianceTaskById(taskId);
        if(complianceTask==null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        List<ComplianceGuide> complianceGuideList=this.complianceGuideDao.findTaskGuidesByTask(complianceTask);
        if(complianceGuideList.isEmpty())
            return new ResponseEntity().noContent();

        return new ResponseEntity().ok(complianceGuideList.stream().map(g->this.responseMapper.mapToGuideResponse(g)));
    }

    @Override
    public ResponseEntity saveTaskGuide(ComplianceGuideRequest guideRequest, Long taskId) {
        ComplianceTask complianceTask=this.complianceTaskDao.findComplianceTaskById(taskId);
        if(complianceTask==null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        ComplianceGuide findComplianceGuide=this.complianceGuideDao.findTaskGuideByTaskAndJurisdiction(complianceTask,guideRequest.getJurisdiction());
        if(findComplianceGuide!=null)
            return new ResponseEntity().badRequest("Compliance Guide already exist !!");

        ComplianceGuide complianceGuide=this.responseMapper.mapToSaveComplianceGuide(guideRequest);
        complianceGuide.setComplianceTask(complianceTask);

        ComplianceGuide saveComplianceGuide=this.complianceGuideDao.saveComplianceGuide(complianceGuide);
        if(saveComplianceGuide==null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity updateTaskGuide(ComplianceGuideRequest guideRequest, Long taskId) {
        ComplianceTask complianceTask=this.complianceTaskDao.findComplianceTaskById(taskId);
        if(complianceTask==null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        ComplianceGuide findComplianceGuide=this.complianceGuideDao.
                findGuideByTaskAndJurisdictionAndIdNot(complianceTask,
                        guideRequest.getJurisdiction(), guideRequest.getId());
        if(findComplianceGuide!=null)
            return new ResponseEntity().badRequest("Compliance Guide already exist !!");

        ComplianceGuide complianceGuide=this.responseMapper.mapToUpdateComplianceGuide(guideRequest);
        complianceGuide.setComplianceTask(complianceTask);

        ComplianceGuide updateComplianceGuide=this.complianceGuideDao.updateComplianceGuide(complianceGuide);
        if(updateComplianceGuide==null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity fetchTaskGuide(Long guideId, Long taskId) {
        ComplianceTask complianceTask=this.complianceTaskDao.findComplianceTaskById(taskId);
        if(complianceTask==null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        ComplianceGuide complianceGuide=this.complianceGuideDao.findGuideByTaskAndId(complianceTask,guideId);
        if(complianceGuide==null)
            return new ResponseEntity().badRequest("Task Guide Not Found !!");

        return new ResponseEntity().ok(this.responseMapper.mapToGuideResponse(complianceGuide));
    }

    @Override
    public ResponseEntity deleteTaskGuide(Long guideId, Long taskId) {
        ComplianceTask complianceTask=this.complianceTaskDao.findComplianceTaskById(taskId);
        if(complianceTask==null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        ComplianceGuide complianceGuide=this.complianceGuideDao.findGuideByTaskAndId(complianceTask,guideId);
        if(complianceGuide==null)
            return new ResponseEntity().badRequest("Task Guide Not Found !!");

        boolean deleteGuide=this.complianceGuideDao.deleteComplianceGuide(complianceGuide);
        if(!deleteGuide)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity fetchGuidesBySubTask(Long subTaskId) {
        ComplianceSubTask complianceSubTask=this.complianceSubTaskDao.findComplianceSubTaskById(subTaskId);
        if(complianceSubTask==null)
            return new ResponseEntity().badRequest("Sub-Task Not Found !!");

        List<ComplianceGuide> complianceGuideList=this.complianceGuideDao.findGuidesBySubTask(complianceSubTask);
        if(complianceGuideList.isEmpty())
            return new ResponseEntity().noContent();

        return new ResponseEntity().ok(complianceGuideList.stream().map(g->this.responseMapper.mapToGuideResponse(g)));
    }

    @Override
    public ResponseEntity saveSubTaskGuide(ComplianceGuideRequest guideRequest, Long subTaskId) {
        ComplianceSubTask complianceSubTask=this.complianceSubTaskDao.findComplianceSubTaskById(subTaskId);
        if(complianceSubTask==null)
            return new ResponseEntity().badRequest("Sub-Task Not Found !!");

        ComplianceGuide findComplianceGuide=this.complianceGuideDao.findGuideBySubTaskAndJurisdiction(complianceSubTask, guideRequest.getJurisdiction());
        if(findComplianceGuide!=null)
            return new ResponseEntity().badRequest("Compliance Guide already exist !!");

        ComplianceGuide complianceGuide=this.responseMapper.mapToSaveComplianceGuide(guideRequest);
        complianceGuide.setComplianceSubTask(complianceSubTask);

        ComplianceGuide saveComplianceGuide=this.complianceGuideDao.saveComplianceGuide(complianceGuide);
        if(saveComplianceGuide==null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity updateSubTaskGuide(ComplianceGuideRequest guideRequest, Long subTaskId) {
        ComplianceSubTask complianceSubTask=this.complianceSubTaskDao.findComplianceSubTaskById(subTaskId);
        if(complianceSubTask==null)
            return new ResponseEntity().badRequest("Sub-Task Not Found !!");

        ComplianceGuide findComplianceGuide=this.complianceGuideDao.findGuideBySubTaskAndJurisdictionAndIdNot(complianceSubTask,guideRequest.getJurisdiction(),guideRequest.getId());
        if(findComplianceGuide!=null)
            return new ResponseEntity().badRequest("Compliance Guide already exist !!");

        ComplianceGuide complianceGuide=this.responseMapper.mapToUpdateComplianceGuide(guideRequest);
        complianceGuide.setComplianceSubTask(complianceSubTask);

        ComplianceGuide updateComplianceGuide=this.complianceGuideDao.updateComplianceGuide(complianceGuide);
        if(updateComplianceGuide==null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity fetchSubTaskGuide(Long guideId, Long subTaskId) {
        ComplianceSubTask complianceSubTask=this.complianceSubTaskDao.findComplianceSubTaskById(subTaskId);
        if(complianceSubTask==null)
            return new ResponseEntity().badRequest("Sub-Task Not Found !!");

        ComplianceGuide complianceGuide=this.complianceGuideDao.findGuideBySubTaskAndId(complianceSubTask,guideId);
        if(complianceGuide==null)
            return new ResponseEntity().badRequest("Task Guide Not Found !!");

        return new ResponseEntity().ok(this.responseMapper.mapToGuideResponse(complianceGuide));
    }

    @Override
    public ResponseEntity deleteSubTaskGuide(Long guideId, Long subTaskId) {
        ComplianceSubTask complianceSubTask=this.complianceSubTaskDao.findComplianceSubTaskById(subTaskId);
        if(complianceSubTask==null)
            return new ResponseEntity().badRequest("Sub-Task Not Found !!");

        ComplianceGuide complianceGuide=this.complianceGuideDao.findGuideBySubTaskAndId(complianceSubTask,guideId);
        if(complianceGuide==null)
            return new ResponseEntity().badRequest("Task Guide Not Found !!");

        boolean deleteGuide=this.complianceGuideDao.deleteComplianceGuide(complianceGuide);
        if(!deleteGuide)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }
}