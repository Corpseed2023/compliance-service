package com.lawzoom.complianceservice.service.legalService;

import com.lawzoom.complianceservice.dto.legalGuideDto.LegalGuideRequest;
import com.lawzoom.complianceservice.response.ResponseEntity;

public interface LegalService {
    ResponseEntity fetchLegalGuides(Long guideId);

    ResponseEntity saveLegalGuide(LegalGuideRequest legalGuideRequest, Long guideId);

    ResponseEntity updateLegalGuide(LegalGuideRequest legalGuideRequest, Long guideId);

    ResponseEntity fetchLegalGuide(Long guideId, Long legalId);

    ResponseEntity deleteLegalGuide(Long guideId, Long legalId);
}
