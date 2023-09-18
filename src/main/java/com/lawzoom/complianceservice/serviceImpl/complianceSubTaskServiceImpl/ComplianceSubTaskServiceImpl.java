package com.lawzoom.complianceservice.serviceImpl.complianceSubTaskServiceImpl;

import com.lawzoom.complianceservice.dao.complianceSubTaskDao.ComplianceSubTaskDao;
import com.lawzoom.complianceservice.dao.complianceTaskDao.ComplianceTaskDao;
import com.lawzoom.complianceservice.dto.commonResponse.MessageResponse;
import com.lawzoom.complianceservice.dto.complianceSubTaskDto.ComplianceSubTaskRequest;
import com.lawzoom.complianceservice.model.complianceSubTaskModel.ComplianceSubTask;
import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.service.complianceSubTaskService.ComplianceSubTaskService;
import com.lawzoom.complianceservice.utility.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplianceSubTaskServiceImpl implements ComplianceSubTaskService {

<<<<<<< HEAD
//    @Autowired
//    private ComplianceSubTaskDao complianceSubtaskDao;
//
//    @Autowired
//    private ComplianceTaskDao complianceTaskDao;

=======
>>>>>>> e2f9bde6e79c463bf4df4ef58e396f001192b9e9
    @Autowired
    private ResponseMapper responseMapper;

    @Override
    public ResponseEntity fetchSubTaskByTask(Long taskId) {

        ComplianceTask complianceTask=this.complianceTaskDao.fetchComplianceTaskById(taskId);
        if(complianceTask==null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        List<ComplianceSubTask> complianceSubTaskList=this.complianceSubtaskDao.findComplianceSubTaskByComplianceTask(complianceTask);
        if(complianceSubTaskList.isEmpty())
            return new ResponseEntity().noContent();

        return new ResponseEntity().ok(complianceSubTaskList.stream()
                .map(t->this.responseMapper.mapToComplianceSubTask(t)));
    }

    @Override
    public ResponseEntity saveSubTask(ComplianceSubTaskRequest subTaskRequest, Long taskId) {
        ComplianceTask complianceTask=this.complianceTaskDao.fetchComplianceTaskById(taskId);
        if(complianceTask==null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        ComplianceSubTask findSubTask=this.complianceSubtaskDao.findSubTaskByComplianceTaskAndName(complianceTask,subTaskRequest.getSubTaskName());
        if(findSubTask!=null)
            return new ResponseEntity().badRequest("Compliance Sub-Task Already exist !!");

        ComplianceSubTask saveSubTask=this.complianceSubtaskDao.saveComplianceSubTask(this.responseMapper.mapToSaveComplianceSubTask(subTaskRequest,complianceTask));
        if(saveSubTask==null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity updateSubTask(ComplianceSubTaskRequest subTaskRequest, Long taskId) {
        ComplianceTask complianceTask=this.complianceTaskDao.fetchComplianceTaskById(taskId);
        if(complianceTask==null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        ComplianceSubTask findSubTask=this.complianceSubtaskDao.findSubTaskByComplianceAndNameAndIdNot(complianceTask,subTaskRequest.getSubTaskName(),subTaskRequest.getId());
        if(findSubTask!=null)
            return new ResponseEntity().badRequest("Compliance Sub-Task Already exist !!");

        ComplianceSubTask updateSubTask=this.complianceSubtaskDao.updateComplianceSubTask(this.responseMapper.mapToUpdateComplianceSubTask(subTaskRequest,complianceTask));
        if(updateSubTask==null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity fetchSubTaskById(Long taskId, Long subTaskId) {
        ComplianceTask complianceTask=this.complianceTaskDao.fetchComplianceTaskById(taskId);
        if(complianceTask==null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        ComplianceSubTask complianceSubTask=this.complianceSubtaskDao.findSubTaskByTaskAndId(complianceTask,subTaskId);
        if(complianceSubTask==null)
            return new ResponseEntity().badRequest("Compliance Sub Task Not Found !!");

        return new ResponseEntity().ok(this.responseMapper.mapToComplianceSubTaskResponse(complianceSubTask));
    }

    @Override
    public ResponseEntity deleteSubTaskById(Long taskId, Long subTaskId) {
        ComplianceTask complianceTask=this.complianceTaskDao.fetchComplianceTaskById(taskId);
        if(complianceTask==null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        ComplianceSubTask complianceSubTask=this.complianceSubtaskDao.findSubTaskByTaskAndId(complianceTask,subTaskId);
        if(complianceSubTask==null)
            return new ResponseEntity().badRequest("Compliance Sub Task Not Found !!");

        boolean deleteSubTask=this.complianceSubtaskDao.deleteComplianceSubTask(complianceSubTask);
        if(!deleteSubTask)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }
}
