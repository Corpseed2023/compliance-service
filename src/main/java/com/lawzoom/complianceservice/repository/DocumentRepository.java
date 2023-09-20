package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.complianceSubTaskModel.ComplianceSubTask;
import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
import com.lawzoom.complianceservice.model.documentModel.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document,Long> {
    List<Document> findAllDocumentsByCompliance(Compliance compliance);

    Document findDocumentById(Long id);

    Document findDocumentByComplianceAndId(Compliance compliance, Long id);

    List<Document> findAllDocumentsByComplianceTask(ComplianceTask complianceTask);

    Document findDocumentByComplianceTaskAndId(ComplianceTask complianceTask, Long id);

    boolean existsByIdAndComplianceTask(Long id, ComplianceTask complianceTask);

    @Modifying
    @Transactional
    @Query("DELETE FROM Document d WHERE d.id = :id AND d.complianceTask = :complianceTask")
    void deleteDocumentByIdAndComplianceTask(@Param("id") Long id, @Param("complianceTask") ComplianceTask complianceTask);

    List<Document> findAllByComplianceSubTask(ComplianceSubTask complianceSubTask);

    Document findDocumentByComplianceSubTaskAndId(ComplianceSubTask complianceSubTask, Long id);
}

