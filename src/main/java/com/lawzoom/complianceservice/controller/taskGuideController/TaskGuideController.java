package com.lawzoom.complianceservice.controller.taskGuideController;

import com.lawzoom.complianceservice.dto.complianceGuideDto.ComplianceGuideRequest;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.service.complianceGuideService.ComplianceGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compliance/task/guide/{taskId}")
public class TaskGuideController {

    @Autowired
    private ComplianceGuideService complianceGuideService;

    @GetMapping()
    public ResponseEntity fetchTaskGuides(@PathVariable("taskId") Long taskId){
        return this.complianceGuideService.fetchGuidesByTask(taskId);
    }

    @PostMapping("/save")
    public ResponseEntity saveTaskGuide(@RequestBody ComplianceGuideRequest guideRequest, @PathVariable("taskId") Long taskId){
        return this.complianceGuideService.saveTaskGuide(guideRequest,taskId);
    }

    @PutMapping("/update")
    public ResponseEntity updateTaskGuide(@RequestBody ComplianceGuideRequest guideRequest, @PathVariable("taskId") Long taskId){
        return this.complianceGuideService.updateTaskGuide(guideRequest,taskId);
    }

    @GetMapping("/{guideId}")
    public ResponseEntity fetchTaskGuide(@PathVariable("guideId") Long guideId,@PathVariable("taskId") Long taskId){
        return this.complianceGuideService.fetchTaskGuide(guideId,taskId);
    }

    @DeleteMapping("/{guideId}")
    public ResponseEntity deleteTaskGuide(@PathVariable("guideId") Long guideId,@PathVariable("taskId") Long taskId){
        return this.complianceGuideService.deleteTaskGuide(guideId,taskId);
    }

}
