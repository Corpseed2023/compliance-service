package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.dto.complianceDto.ComplianceRequest;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplianceRepo extends JpaRepository<Compliance ,Long>

{

    List<Compliance> findCompliancesByBusinessUnitId(Long businessUnitId);

    List<Compliance> findAllByBusinessUnitId(Long businessUnitId);


    Compliance updateCompliance(ComplianceRequest complianceRequest, long l, Long businessUnitId);

    Compliance findByIdAndBusinessUnitId(Long complianceId, Long businessUnitId);
}
