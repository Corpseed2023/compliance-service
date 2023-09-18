package com.lawzoom.complianceservice.serviceImpl.complianceSubTaskServiceImpl;

import com.lawzoom.complianceservice.dto.complianceSubTaskDto.ComplianceSubTaskRequest;
import com.lawzoom.complianceservice.model.complianceSubTaskModel.ComplianceSubTask;
import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
import com.lawzoom.complianceservice.repository.ComplianceSubTaskRepository;
import com.lawzoom.complianceservice.repository.ComplianceTaskRepository;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.service.complianceSubTaskService.ComplianceSubTaskService;
import com.lawzoom.complianceservice.utility.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComplianceSubTaskServiceImpl implements ComplianceSubTaskService {



    private ComplianceTaskRepository complianceTaskRepository;

    private ComplianceSubTaskRepository complianceSubTaskRepository;


    @Autowired
    private ResponseMapper responseMapper;

    @Override
    public ResponseEntity fetchSubTaskByTask(Long taskId) {

//        ComplianceTask complianceTask=this.complianceTaskRepository.fetchComplianceTaskById(taskId);
        Optional<ComplianceTask> complianceTask = this.complianceTaskRepository.findById(taskId);

        if (complianceTask.isPresent())
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

//        List<ComplianceSubTask> complianceSubTaskList=this.complianceSubTaskRepo.findComplianceSubTaskByComplianceTask(complianceTask);
        List<ComplianceSubTask> complianceSubTaskList = complianceSubTaskRepository.findByComplianceTask(complianceTask.get());

        if (complianceSubTaskList.isEmpty())
            return new ResponseEntity().noContent();

        return new ResponseEntity().ok(complianceSubTaskList.stream().map(t -> this.responseMapper.mapToComplianceSubTask(t)));

    }

//    @Override
//    public ResponseEntity saveSubTask(ComplianceSubTaskRequest subTaskRequest, Long taskId) {
////        ComplianceTask complianceTask=this.complianceTaskRepository.fetchComplianceTaskById(taskId);
//
//        Optional<ComplianceTask> complianceTask = this.complianceTaskRepository.findById(taskId);
//
//        if(complianceTask==null)
//
//            return new ResponseEntity().badRequest("Compliance Task Not Found !!");
//
//        ComplianceSubTask findSubTask=this.complianceSubTaskRepository.findSubTaskByComplianceTaskAndName(complianceTask,subTaskRequest.getSubTaskName());
//
//        if(findSubTask!=null)
//            return new ResponseEntity().badRequest("Compliance Sub-Task Already exist !!");
//
//        ComplianceSubTask saveSubTask=this.complianceSubTaskRepository.saveComplianceSubTask(this.responseMapper.mapToSaveComplianceSubTask(subTaskRequest,complianceTask));
//        if(saveSubTask==null)
//            return new ResponseEntity().internalServerError();
//
//        return new ResponseEntity().ok();
//    }

    public ResponseEntity saveSubTask(ComplianceSubTaskRequest subTaskRequest, Long taskId) {
        Optional<ComplianceTask> complianceTaskOptional = complianceTaskRepository.findById(taskId);

        if (!complianceTaskOptional.isPresent()) {
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        }

        ComplianceTask complianceTask = complianceTaskOptional.get();

        ComplianceSubTask findSubTask = complianceSubTaskRepository.
                findSubTaskByComplianceTaskAndName(complianceTask, subTaskRequest.getSubTaskName());

        if (findSubTask != null) {
//            return ResponseEntity.badRequest().body("Compliance Sub-Task Already exists !!");

            return new ResponseEntity().badRequest("Compliance Sub-Task Already exists");
        }

        ComplianceSubTask saveSubTask = complianceSubTaskRepository.save(
                responseMapper.mapToSaveComplianceSubTask(subTaskRequest, complianceTask)
        );

        if (saveSubTask == null) {
            return new ResponseEntity().internalServerError();
        }

        return ResponseEntity.ok().build();
    }


    @Override
    public ResponseEntity updateSubTask(ComplianceSubTaskRequest subTaskRequest, Long taskId) {
//        ComplianceTask complianceTask=this.complianceTaskDao.fetchComplianceTaskById(taskId);

        Optional<ComplianceTask> complianceTaskData = this.complianceTaskRepository.findById(taskId);

        if (complianceTaskData.isPresent())

            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        ComplianceTask complianceTask = complianceTaskData.get();


//        ComplianceSubTask findSubTask=this.complianceSubTaskRepository.findSubTaskByComplianceAndNameAndIdNot(complianceTask,subTaskRequest.getSubTaskName(),subTaskRequest.getId());
        ComplianceSubTask findSubTask = complianceSubTaskRepository.
                findSubTaskByComplianceTaskAndName(complianceTask, subTaskRequest.getSubTaskName());

        if (findSubTask != null)
            return new ResponseEntity().badRequest("Compliance Sub-Task Already exist !!");

//        ComplianceSubTask updateSubTask=this.complianceSubTaskRepository
//        .updateComplianceSubTask(this.responseMapper.mapToUpdateComplianceSubTask(subTaskRequest,complianceTask));

        ComplianceSubTask mapupdateSubTask = responseMapper.mapToUpdateComplianceSubTask(subTaskRequest, complianceTask);
        ComplianceSubTask updatedSubTask = complianceSubTaskRepository.save(mapupdateSubTask);

        if (updatedSubTask == null)

            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity fetchSubTaskById(Long taskId, Long subTaskId) {
        Optional<ComplianceTask> complianceTask = this.complianceTaskRepository.findById(taskId);

        if (complianceTask == null)

            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        ComplianceSubTask complianceSubTask = this.complianceSubTaskRepository.findByComplianceTaskAndId(complianceTask, subTaskId);
        //        ComplianceSubTask complianceSubTask=this.complianceSubTaskRepository.findSubTaskByComplianceTaskAndSubTaskId(complianceTask,subTaskId);

        if (complianceSubTask == null)
            return new ResponseEntity().badRequest("Compliance Sub Task Not Found !!");

        return new ResponseEntity().ok(this.responseMapper.mapToComplianceSubTaskResponse(complianceSubTask));
    }

//    @Override
//    public ResponseEntity fetchSubTaskById(Long taskId, Long subTaskId) {
//        ComplianceTask complianceTask=this.complianceTaskRepository.fetchComplianceTaskById(taskId);
//        if(complianceTask==null)
//            return new ResponseEntity().badRequest("Compliance Task Not Found !!");
//
//        ComplianceSubTask complianceSubTask=this.complianceSubTaskRepository.findSubTaskByTaskAndId(complianceTask,subTaskId);
//        if(complianceSubTask==null)
//            return new ResponseEntity().badRequest("Compliance Sub Task Not Found !!");
//
//        return new ResponseEntity().ok(this.responseMapper.mapToComplianceSubTaskResponse(complianceSubTask));
//    }


    @Override
    public ResponseEntity deleteSubTaskById(Long taskId, Long subTaskId) {

        Optional<ComplianceTask> complianceTask = this.complianceTaskRepository.findById(taskId);

        if (complianceTask == null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        ComplianceSubTask complianceSubTask = this.complianceSubTaskRepository.findByComplianceTaskAndId(complianceTask, subTaskId);
        if (complianceSubTask == null)
            return new ResponseEntity().badRequest("Compliance Sub Task Not Found !!");

//        this.complianceSubTaskRepository.delete(complianceSubTask);

//        if(!deleteSubTask)
//            return new ResponseEntity().internalServerError();
//
//        return new ResponseEntity().ok();
//    }

        try {
            this.complianceTaskRepository.delete(complianceSubTask.getComplianceTask());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete compliance task");
            return new ResponseEntity().internalServerError().badRequest("Failed to delete Sub compliance task");

        }
    }
}
