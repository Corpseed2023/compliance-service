package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.model.complianceSubTaskModel.ComplianceSubTask;
import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplianceSubTaskRepository extends JpaRepository<ComplianceSubTask,Long>

{

    List<ComplianceSubTask> findByComplianceTask(ComplianceTask complianceTask);

    ComplianceSubTask findSubTaskByComplianceTaskAndName(ComplianceTask complianceTask, String subTaskName);

    ComplianceSubTask findSubTaskByComplianceTaskAndSubTaskId(Optional<ComplianceTask> complianceTask, Long subTaskId);

    ComplianceSubTask findByComplianceTaskAndId(Optional<ComplianceTask> complianceTask, Long subTaskId);

    ComplianceSubTask findComplianceSubTaskById(Long subTaskId);
}
