package com.lawzoom.complianceservice.controller.legalGuideController;

import com.lawzoom.complianceservice.dto.legalGuideDto.LegalGuideRequest;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.service.legalService.LegalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compliance/guide/legal/{guideId}")
public class LegalGuideController {

    @Autowired
    private LegalService legalService;

    @GetMapping()
    public ResponseEntity fetchLegalGuides(@PathVariable("guideId") Long guideId){
        return this.legalService.fetchLegalGuides(guideId);
    }

    @PostMapping("/save")
    public ResponseEntity saveLegalGuide(@RequestBody LegalGuideRequest legalGuideRequest,@PathVariable("guideId") Long guideId){
        return this.legalService.saveLegalGuide(legalGuideRequest,guideId);
    }

    @PutMapping("/update")
    public ResponseEntity updateLegalGuide(@RequestBody LegalGuideRequest legalGuideRequest,@PathVariable("guideId") Long guideId){
        return this.legalService.updateLegalGuide(legalGuideRequest,guideId);
    }

    @GetMapping("/{legalId}")
    public ResponseEntity fetchLegalGuide(@PathVariable("guideId") Long guideId,@PathVariable("legalId") Long legalId){
        return this.legalService.fetchLegalGuide(guideId,legalId);
    }

    @DeleteMapping("/{legalId}")
    public ResponseEntity deleteLegalGuide(@PathVariable("guideId") Long guideId,@PathVariable("legalId") Long legalId){
        return this.legalService.deleteLegalGuide(guideId,legalId);
    }
}
