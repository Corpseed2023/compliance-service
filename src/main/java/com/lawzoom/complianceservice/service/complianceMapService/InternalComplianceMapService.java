package com.lawzoom.complianceservice.service.complianceMapService;

import com.lawzoom.complianceservice.dto.businessUnitDto.BusinessUnitResponse;
import com.lawzoom.complianceservice.dto.companyResponseDto.CompanyResponse;
import com.lawzoom.complianceservice.response.ResponseEntity;

public interface InternalComplianceMapService {

    ResponseEntity<?> mapCompanyCompliances(CompanyResponse companyResponse);

    ResponseEntity<?> mapBusinessUnitCompliances(BusinessUnitResponse businessUnitResponse);
}
