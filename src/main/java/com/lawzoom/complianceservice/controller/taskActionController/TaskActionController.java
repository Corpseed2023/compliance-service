package com.lawzoom.complianceservice.controller.taskActionController;

import com.lawzoom.complianceservice.dto.taskActionDto.TaskActionRequest;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.service.taskActionService.TaskActionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compliance/task/action/{taskId}")
public class TaskActionController {

    private TaskActionService taskActionService;

    public TaskActionController(TaskActionService taskActionService) {
        this.taskActionService = taskActionService;
    }

    @GetMapping
    public ResponseEntity<?> fetchTaskActions(@PathVariable("taskId") Long taskId){
        return this.taskActionService.findTaskActionsByTask(taskId);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveTask(@RequestBody TaskActionRequest taskActionRequest, @PathVariable("taskId") Long taskId){
        return this.taskActionService.saveTaskAction(taskActionRequest,taskId);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTask(@RequestBody TaskActionRequest taskActionRequest,@PathVariable("taskId") Long taskId){
        return this.taskActionService.updateTaskAction(taskActionRequest,taskId);
    }

    @GetMapping("/{actionId}")
    public ResponseEntity<?> fetchTaskById(@PathVariable("taskId") Long taskId,@PathVariable("actionId") Long actionId){
        return this.taskActionService.fetchTaskActionById(taskId,actionId);
    }

    @DeleteMapping("/{actionId}")
    public ResponseEntity<?> deleteTaskById(@PathVariable("taskId") Long taskId,@PathVariable("actionId") Long actionId){
        return this.taskActionService.deleteTaskActionById(taskId,actionId);
    }
}
