package com.lawzoom.complianceservice.feignclient;

import com.lawzoom.complianceservice.dto.compliancemap.ComplianceMasterResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "COMPLIANCE-MASTER-SERVICE")
public interface ComplianceMasterConsumer {

    @GetMapping("/master/compliance/by-activity")
    List<ComplianceMasterResponse> fetchComplianceByBusinessActivity(@RequestParam("businessActivity") String businessActivity);
}
