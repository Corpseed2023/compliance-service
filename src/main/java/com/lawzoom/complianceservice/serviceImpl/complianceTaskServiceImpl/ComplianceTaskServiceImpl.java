package com.lawzoom.complianceservice.serviceImpl.complianceTaskServiceImpl;

import com.lawzoom.complianceservice.dao.complianceDao.ComplianceDao;
import com.lawzoom.complianceservice.dao.complianceTaskDao.ComplianceTaskDao;
import com.lawzoom.complianceservice.dto.complianceTaskDto.ComplianceTaskRequest;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.service.complianceTaskService.ComplianceTaskService;
import com.lawzoom.complianceservice.utility.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComplianceTaskServiceImpl implements ComplianceTaskService {

//    @Autowired
//    private ComplianceTaskDao complianceTaskDao;

//    @Autowired
//    private ComplianceDao complianceDao;

    @Autowired
    private ResponseMapper responseMapper;

    @Override
    public ResponseEntity findComplianceTaskByCompliance(Long complianceId) {
        Compliance compliance=this.complianceDao.findComplianceById(complianceId);
        if(compliance==null)
            return new ResponseEntity().badRequest("Compliance Not Found !!");

        List<ComplianceTask> complianceTaskList=this.complianceTaskDao.findComplianceTaskByCompliance(compliance);
        if(complianceTaskList.isEmpty())
            return new ResponseEntity().noContent();

        return new ResponseEntity().ok(complianceTaskList.stream().map(t->this.responseMapper.mapToComplianceTaskResponse(t)).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity saveTask(ComplianceTaskRequest taskRequest, Long complianceId) {
        Compliance compliance=this.complianceDao.findComplianceById(complianceId);
        if(compliance==null)
            return new ResponseEntity().badRequest("Compliance Not Found !!");

        ComplianceTask findTask=this.complianceTaskDao.findTaskByComplianceAndTaskName(compliance,taskRequest.getTaskName());
        if(findTask!=null)
            return new ResponseEntity().badRequest("Compliance Task already exist !!");

        ComplianceTask saveTask=this.complianceTaskDao.saveComplianceTask(this.responseMapper.mapToSaveComplianceTask(taskRequest,compliance));
        if(saveTask==null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity updateTask(ComplianceTaskRequest taskRequest, Long complianceId) {
        Compliance compliance=this.complianceDao.findComplianceById(complianceId);
        if(compliance==null)
            return new ResponseEntity().badRequest("Compliance Not Found !!");

        ComplianceTask findTask=this.complianceTaskDao.findTaskByComplianceAndTaskNameAndIdNot(compliance,taskRequest.getTaskName(),taskRequest.getId());
        if(findTask!=null)
            return new ResponseEntity().badRequest("Compliance Task already exist !!");

        ComplianceTask updateTask=this.complianceTaskDao.updateComplianceTask(this.responseMapper.mapToUpdateComplianceTask(taskRequest,compliance));
        if(updateTask==null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity fetchTaskById(Long complianceId, Long taskId) {
        Compliance compliance=this.complianceDao.findComplianceById(complianceId);
        if(compliance==null)
            return new ResponseEntity().badRequest("Compliance Not Found !!");

        ComplianceTask complianceTask=this.complianceTaskDao.fetchTaskByComplianceAndId(compliance,taskId);
        if(complianceTask==null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        return new ResponseEntity().ok(this.responseMapper.mapToComplianceTaskResponse(complianceTask));
    }

    @Override
    public ResponseEntity deleteTaskById(Long complianceId, Long taskId) {
        Compliance compliance=this.complianceDao.findComplianceById(complianceId);
        if(compliance==null)
            return new ResponseEntity().badRequest("Compliance Not Found !!");

        ComplianceTask complianceTask=this.complianceTaskDao.fetchTaskByComplianceAndId(compliance,taskId);
        if(complianceTask==null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        boolean deleteTask=this.complianceTaskDao.deleteComplianceTask(complianceTask);
        if(!deleteTask)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }
}
