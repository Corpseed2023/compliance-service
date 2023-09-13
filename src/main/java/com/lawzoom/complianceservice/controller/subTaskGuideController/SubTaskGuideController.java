package com.lawzoom.complianceservice.controller.subTaskGuideController;

import com.lawzoom.complianceservice.dto.complianceGuideDto.ComplianceGuideRequest;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.service.complianceGuideService.ComplianceGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compliance/sub-task/guide/{subTaskId}")
public class SubTaskGuideController {

    @Autowired
    private ComplianceGuideService complianceGuideService;

    @GetMapping()
    public ResponseEntity fetchSubTaskGuides(@PathVariable("subTaskId") Long subTaskId){
        return this.complianceGuideService.fetchGuidesBySubTask(subTaskId);
    }

    @PostMapping("/save")
    public ResponseEntity saveSubTaskGuide(@RequestBody ComplianceGuideRequest guideRequest, @PathVariable("subTaskId") Long subTaskId){
        return this.complianceGuideService.saveSubTaskGuide(guideRequest,subTaskId);
    }

    @PutMapping("/update")
    public ResponseEntity updateSubTaskGuide(@RequestBody ComplianceGuideRequest guideRequest, @PathVariable("subTaskId") Long subTaskId){
        return this.complianceGuideService.updateSubTaskGuide(guideRequest,subTaskId);
    }

    @GetMapping("/{guideId}")
    public ResponseEntity fetchSubTaskGuide(@PathVariable("guideId") Long guideId,@PathVariable("subTaskId") Long subTaskId){
        return this.complianceGuideService.fetchSubTaskGuide(guideId,subTaskId);
    }

    @DeleteMapping("/{guideId}")
    public ResponseEntity deleteSubTaskGuide(@PathVariable("guideId") Long guideId,@PathVariable("subTaskId") Long subTaskId){
        return this.complianceGuideService.deleteSubTaskGuide(guideId,subTaskId);
    }
}
