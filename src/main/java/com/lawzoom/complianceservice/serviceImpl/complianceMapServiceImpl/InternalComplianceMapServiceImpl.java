package com.lawzoom.complianceservice.serviceImpl.complianceMapServiceImpl;

import com.lawzoom.complianceservice.dto.businessUnitDto.BusinessUnitResponse;
import com.lawzoom.complianceservice.dto.companyResponseDto.CompanyResponse;
import com.lawzoom.complianceservice.dto.compliancemap.ComplianceMasterResponse;
import com.lawzoom.complianceservice.feignclient.ComplianceMasterConsumer;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.service.complianceMapService.InternalComplianceMapService;
import com.lawzoom.complianceservice.service.complianceService.ComplianceService;
import com.lawzoom.complianceservice.utility.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InternalComplianceMapServiceImpl implements InternalComplianceMapService {

    @Autowired
    private ComplianceMasterConsumer complianceMasterConsumer;

    @Autowired
    private ComplianceService complianceService;

    @Autowired
    private ResponseMapper responseMapper;

    @Override
    public ResponseEntity<?> mapCompanyCompliances(CompanyResponse companyResponse) {
        List<ComplianceMasterResponse> complianceMasterResponses = this.complianceMasterConsumer.fetchComplianceByBusinessActivity
                (companyResponse.getBusinessActivity());

        if(complianceMasterResponses.isEmpty()||complianceMasterResponses==null)
            return new ResponseEntity<>().noContent();

        List<Compliance> complianceList = complianceMasterResponses.stream().map(
                c -> this.responseMapper.mapToSaveComplianceInternal(
                        c, companyResponse.getCompanyId(), 0L))
                .collect(Collectors.toList());
        System.out.println("Printing returned compliance list......start");
        complianceList.forEach(c-> System.out.println(c));
        System.out.println("Printing returned compliance list......end");
        this.complianceService.saveAllCompliances(complianceList);

        return new ResponseEntity<>().ok();
    }

    @Override
    public ResponseEntity<?> mapBusinessUnitCompliances(BusinessUnitResponse businessUnitResponse) {
        List<ComplianceMasterResponse> complianceMasterResponses = this.complianceMasterConsumer.fetchComplianceByBusinessActivity
                (businessUnitResponse.getBusinessActivity());

        if(complianceMasterResponses.isEmpty()||complianceMasterResponses==null)
            return new ResponseEntity<>().noContent();

        this.complianceService.saveAllCompliances(complianceMasterResponses.stream().map(
                c->this.responseMapper.mapToSaveComplianceInternal(
                        c,0L,businessUnitResponse.getId()))
                .collect(Collectors.toList()));

        return new ResponseEntity<>().ok();
    }
}
