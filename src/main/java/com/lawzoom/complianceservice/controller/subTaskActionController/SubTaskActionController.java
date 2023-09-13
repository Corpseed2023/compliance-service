package com.lawzoom.complianceservice.controller.subTaskActionController;

import com.lawzoom.complianceservice.dto.taskActionDto.TaskActionRequest;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.service.taskActionService.TaskActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compliance/sub-task/action/{subTaskId}")
public class SubTaskActionController {

    @Autowired
    private TaskActionService taskActionService;

    public SubTaskActionController(TaskActionService taskActionService) {
        this.taskActionService = taskActionService;
    }

    @GetMapping
    public ResponseEntity fetchSubTaskActions(@PathVariable("subTaskId") Long subTaskId){
        return this.taskActionService.findTaskActionsBySubTask(subTaskId);
    }

    @PostMapping("/save")
    public ResponseEntity saveSubTaskAction(@RequestBody TaskActionRequest taskActionRequest, @PathVariable("subTaskId") Long subTaskId){
        return this.taskActionService.saveSubTaskAction(taskActionRequest,subTaskId);
    }

    @PutMapping("/update")
    public ResponseEntity updateSubTaskAction(@RequestBody TaskActionRequest taskActionRequest,@PathVariable("subTaskId") Long subTaskId){
        return this.taskActionService.updateSubTaskAction(taskActionRequest,subTaskId);
    }

    @GetMapping("/{actionId}")
    public ResponseEntity fetchTaskById(@PathVariable("subTaskId") Long subTaskId,@PathVariable("actionId") Long actionId){
        return this.taskActionService.fetchSubTaskActionById(subTaskId,actionId);
    }

    @DeleteMapping("/{actionId}")
    public ResponseEntity deleteTaskById(@PathVariable("subTaskId") Long subTaskId,@PathVariable("actionId") Long actionId){
        return this.taskActionService.deleteSubTaskActionById(subTaskId,actionId);
    }
}
