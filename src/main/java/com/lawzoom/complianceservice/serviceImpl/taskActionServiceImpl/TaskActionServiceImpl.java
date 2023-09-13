package com.lawzoom.complianceservice.serviceImpl.taskActionServiceImpl;

import com.lawzoom.complianceservice.dao.complianceSubTaskDao.ComplianceSubTaskDao;
import com.lawzoom.complianceservice.dao.complianceTaskDao.ComplianceTaskDao;
import com.lawzoom.complianceservice.dao.taskActionDao.TaskActionDao;
import com.lawzoom.complianceservice.dto.taskActionDto.TaskActionRequest;
import com.lawzoom.complianceservice.model.complianceSubTaskModel.ComplianceSubTask;
import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
import com.lawzoom.complianceservice.model.taskActionModel.TaskAction;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.service.taskActionService.TaskActionService;
import com.lawzoom.complianceservice.utility.ResponseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskActionServiceImpl implements TaskActionService {

    private TaskActionDao taskActionDao;

    private ComplianceTaskDao complianceTaskDao;

    private ComplianceSubTaskDao complianceSubTaskDao;

    private ResponseMapper responseMapper;

    public TaskActionServiceImpl(TaskActionDao taskActionDao, ComplianceTaskDao complianceTaskDao,
                                 ComplianceSubTaskDao complianceSubTaskDao, ResponseMapper responseMapper) {
        this.taskActionDao = taskActionDao;
        this.complianceTaskDao = complianceTaskDao;
        this.complianceSubTaskDao = complianceSubTaskDao;
        this.responseMapper = responseMapper;
    }

    @Override
    public ResponseEntity findTaskActionsByTask(Long taskId) {
        ComplianceTask complianceTask=this.complianceTaskDao.findComplianceTaskById(taskId);
        if(complianceTask==null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        List<TaskAction> taskActionList=this.taskActionDao.findTaskActionsByComplianceTask(complianceTask);
        if(taskActionList.isEmpty())
            return new ResponseEntity().noContent();

        return new ResponseEntity().ok(taskActionList.stream().map(t->this.responseMapper.mapToTaskActionResponse(t)));
    }

    @Override
    public ResponseEntity saveTaskAction(TaskActionRequest taskActionRequest, Long taskId) {
        ComplianceTask complianceTask=this.complianceTaskDao.findComplianceTaskById(taskId);
        if(complianceTask==null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        TaskAction taskAction=this.responseMapper.mapToSaveTaskAction(taskActionRequest);
        taskAction.setComplianceTask(complianceTask);

        TaskAction saveTaskAction=this.taskActionDao.saveTaskAction(taskAction);
        if(saveTaskAction==null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity updateTaskAction(TaskActionRequest taskActionRequest, Long taskId) {
        ComplianceTask complianceTask=this.complianceTaskDao.findComplianceTaskById(taskId);
        if(complianceTask==null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        TaskAction taskAction=this.responseMapper.mapToUpdateTaskAction(taskActionRequest);
        taskAction.setComplianceTask(complianceTask);

        TaskAction updateTaskAction=this.taskActionDao.updateTaskAction(taskAction);
        if(updateTaskAction==null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity fetchTaskActionById(Long taskId, Long actionId) {
        ComplianceTask complianceTask=this.complianceTaskDao.findComplianceTaskById(taskId);
        if(complianceTask==null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        TaskAction taskAction=this.taskActionDao.findTaskActionByComplianceTaskAndId(complianceTask,actionId);
        if(taskAction==null)
            return new ResponseEntity().badRequest("Task Action Not Found !!");
        return new ResponseEntity().ok(this.responseMapper.mapToTaskActionResponse(taskAction));
    }

    @Override
    public ResponseEntity deleteTaskActionById(Long taskId, Long actionId) {
        ComplianceTask complianceTask=this.complianceTaskDao.findComplianceTaskById(taskId);
        if(complianceTask==null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        TaskAction taskAction=this.taskActionDao.findTaskActionByComplianceTaskAndId(complianceTask,actionId);
        if(taskAction==null)
            return new ResponseEntity().badRequest("Task Action Not Found !!");

        boolean deleteTaskAction=this.taskActionDao.deleteTaskAction(taskAction);
        if(!deleteTaskAction)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity findTaskActionsBySubTask(Long subTaskId) {
        ComplianceSubTask complianceSubTask=this.complianceSubTaskDao.findComplianceSubTaskById(subTaskId);
        if(complianceSubTask==null)
            return new ResponseEntity().badRequest("Compliance Sub-Task Not Found !!");

        List<TaskAction> taskActionList=this.taskActionDao.findTaskActionsByComplianceSubTask(complianceSubTask);
        if(taskActionList.isEmpty())
            return new ResponseEntity().noContent();
        return new ResponseEntity().ok(taskActionList.stream().map(t->this.responseMapper.mapToTaskActionResponse(t)));
    }

    @Override
    public ResponseEntity saveSubTaskAction(TaskActionRequest taskActionRequest, Long subTaskId) {
        ComplianceSubTask complianceSubTask=this.complianceSubTaskDao.findComplianceSubTaskById(subTaskId);
        if(complianceSubTask==null)
            return new ResponseEntity().badRequest("Compliance Sub-Task Not Found !!");

        TaskAction taskAction=this.responseMapper.mapToSaveTaskAction(taskActionRequest);
        taskAction.setComplianceSubTask(complianceSubTask);

        TaskAction saveTaskAction=this.taskActionDao.saveTaskAction(taskAction);
        if(saveTaskAction==null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity updateSubTaskAction(TaskActionRequest taskActionRequest, Long subTaskId) {
        ComplianceSubTask complianceSubTask=this.complianceSubTaskDao.findComplianceSubTaskById(subTaskId);
        if(complianceSubTask==null)
            return new ResponseEntity().badRequest("Compliance Sub-Task Not Found !!");

        TaskAction taskAction=this.responseMapper.mapToUpdateTaskAction(taskActionRequest);
        taskAction.setComplianceSubTask(complianceSubTask);

        TaskAction updateTaskAction=this.taskActionDao.updateTaskAction(taskAction);
        if(updateTaskAction==null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity fetchSubTaskActionById(Long subTaskId, Long actionId) {
        ComplianceSubTask complianceSubTask=this.complianceSubTaskDao.findComplianceSubTaskById(subTaskId);
        if(complianceSubTask==null)
            return new ResponseEntity().badRequest("Compliance Sub-Task Not Found !!");

        TaskAction taskAction=this.taskActionDao.findTaskActionByComplianceSubTaskAndId(complianceSubTask,actionId);
        if(taskAction==null)
            return new ResponseEntity().badRequest("Task Action Not Found !!");
        return new ResponseEntity().ok(this.responseMapper.mapToTaskActionResponse(taskAction));
    }

    @Override
    public ResponseEntity deleteSubTaskActionById(Long subTaskId, Long actionId) {
        ComplianceSubTask complianceSubTask=this.complianceSubTaskDao.findComplianceSubTaskById(subTaskId);
        if(complianceSubTask==null)
            return new ResponseEntity().badRequest("Compliance Sub-Task Not Found !!");

        TaskAction taskAction=this.taskActionDao.findTaskActionByComplianceSubTaskAndId(complianceSubTask,actionId);
        if(taskAction==null)
            return new ResponseEntity().badRequest("Task Action Not Found !!");

        boolean deleteTaskAction=this.taskActionDao.deleteTaskAction(taskAction);
        if(!deleteTaskAction)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }
}
