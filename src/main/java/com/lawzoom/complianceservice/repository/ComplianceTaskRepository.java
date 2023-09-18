package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplianceTaskRepository {
    List<ComplianceTask> findComplianceTaskByCompliance(Compliance compliance);

    ComplianceTask findTaskByComplianceAndTaskName(Compliance compliance, String taskName);

}
