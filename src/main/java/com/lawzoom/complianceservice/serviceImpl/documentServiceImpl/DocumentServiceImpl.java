package com.lawzoom.complianceservice.serviceImpl.documentServiceImpl;

import com.lawzoom.complianceservice.dao.complianceDao.ComplianceDao;
import com.lawzoom.complianceservice.dao.complianceSubTaskDao.ComplianceSubTaskDao;
import com.lawzoom.complianceservice.dao.complianceTaskDao.ComplianceTaskDao;
import com.lawzoom.complianceservice.dao.documentDao.DocumentDao;
import com.lawzoom.complianceservice.dto.documentDto.DocumentRequest;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.complianceSubTaskModel.ComplianceSubTask;
import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
import com.lawzoom.complianceservice.model.documentModel.Document;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.service.azureBlobAdapterService.AzureBlobAdapterService;
import com.lawzoom.complianceservice.service.documentService.DocumentService;
import com.lawzoom.complianceservice.utility.CommonUtil;
import com.lawzoom.complianceservice.utility.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentDao documentDao;

    @Autowired
    private ComplianceDao complianceDao;

    @Autowired
    private ComplianceTaskDao complianceTaskDao;

    @Autowired
    private ComplianceSubTaskDao complianceSubTaskDao;

    @Autowired
    private ResponseMapper responseMapper;

    @Autowired
    private AzureBlobAdapterService azureBlobAdapterService;

    @Override
    public ResponseEntity fetchAllComplianceDocument(Long complianceId) {
        Compliance compliance = this.complianceDao.findComplianceById(complianceId);
        if (compliance == null)
            return new ResponseEntity().badRequest("Compliance Not Found !!");

        List<Document> documentList = this.documentDao.findAllDocumentsByCompliance(compliance);
        if (documentList.isEmpty())
            return new ResponseEntity().noContent();

        return new ResponseEntity().ok(documentList.stream().map(d -> this.responseMapper.mapToDocumentResponse(d)));
    }

    @Override
    public ResponseEntity saveComplianceDocument(DocumentRequest documentRequest, Optional<MultipartFile> file, Long complianceId) {
        Compliance compliance = this.complianceDao.findComplianceById(complianceId);
        if (compliance == null)
            return new ResponseEntity().badRequest("Compliance Not Found !!");

        Document document = this.responseMapper.mapToSaveDocument(documentRequest,
                compliance,null,null);

        if (file.isPresent() && !file.get().isEmpty()) {
            String fileName = this.azureBlobAdapterService.upload(file.get(), CommonUtil.getUniqueCode());
            document.setFileName(fileName);
            document.setUploadDate(CommonUtil.getDate());
        }

        document.setCompliance(compliance);
        Document saveDocument = this.documentDao.saveDocument(document);
        if (saveDocument == null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity updateComplianceDocument(DocumentRequest documentRequest, Optional<MultipartFile> file, Long complianceId) {
        Compliance compliance = this.complianceDao.findComplianceById(complianceId);
        if (compliance == null)
            return new ResponseEntity().badRequest("Compliance Not Found !!");

        Document document = this.responseMapper.mapToUpdateDocument(documentRequest,
                compliance,null,null);

        if (file.isPresent() && !file.get().isEmpty()) {
            if (document.getFileName() != null && document.getFileName().length() > 0)
                this.azureBlobAdapterService.deleteFile(document.getFileName());

            String fileName = this.azureBlobAdapterService.upload(file.get(), CommonUtil.getUniqueCode());
            document.setFileName(fileName);
            document.setUploadDate(CommonUtil.getDate());
        } else {
            Document findDocument = this.documentDao.findDocumentById(documentRequest.getId());
            document.setFileName(findDocument.getFileName());
        }


        document.setCompliance(compliance);
        Document updateDocument = this.documentDao.updateDocument(document);
        if (updateDocument == null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity fetchComplianceDocument(Long id, Long complianceId) {
        Compliance compliance = this.complianceDao.findComplianceById(complianceId);
        if (compliance == null)
            return new ResponseEntity().badRequest("Compliance Not Found !!");

        Document document = this.documentDao.findDocumentByComplianceAndId(compliance, id);
        if (document == null)
            return new ResponseEntity().badRequest("Document Not Found !!");

        return new ResponseEntity().ok(this.responseMapper.mapToDocumentResponse(document));
    }

    @Override
    public ResponseEntity deleteComplianceDocument(Long id, Long complianceId) {
        Compliance compliance = this.complianceDao.findComplianceById(complianceId);
        if (compliance == null)
            return new ResponseEntity().badRequest("Compliance Not Found !!");

        Document document = this.documentDao.findDocumentByComplianceAndId(compliance, id);
        if (document == null)
            return new ResponseEntity().badRequest("Document Not Found !!");

        boolean deleteDocument = this.documentDao.deleteDocument(document);
        if (!deleteDocument)
            return new ResponseEntity().internalServerError();

        if (document.getFileName() != null)
            this.azureBlobAdapterService.deleteFile(document.getFileName());

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity fetchAllTaskDocument(Long taskId) {
        ComplianceTask complianceTask = this.complianceTaskDao.findComplianceTaskById(taskId);
        if (complianceTask == null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        List<Document> documentList = this.documentDao.findAllDocumentsByComplianceTask(complianceTask);
        if (documentList.isEmpty())
            return new ResponseEntity().noContent();

        return new ResponseEntity().ok(documentList.stream().map(d -> this.responseMapper.mapToDocumentResponse(d)));
    }

    @Override
    public ResponseEntity saveTaskDocument(DocumentRequest documentRequest, Optional<MultipartFile> file, Long taskId) {
        ComplianceTask complianceTask = this.complianceTaskDao.findComplianceTaskById(taskId);
        if (complianceTask == null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        Document document = this.responseMapper.mapToSaveDocument(documentRequest,null,complianceTask,null);

        if (file.isPresent() && !file.get().isEmpty()) {
            String fileName = this.azureBlobAdapterService.upload(file.get(), CommonUtil.getUniqueCode());
            document.setFileName(fileName);
            document.setUploadDate(CommonUtil.getDate());
        }

        document.setComplianceTask(complianceTask);
        Document saveDocument = this.documentDao.saveDocument(document);
        if (saveDocument == null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity updateTaskDocument(DocumentRequest documentRequest, Optional<MultipartFile> file, Long taskId) {
        ComplianceTask complianceTask = this.complianceTaskDao.findComplianceTaskById(taskId);
        if (complianceTask == null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        Document document = this.responseMapper.mapToUpdateDocument(documentRequest,
                null,complianceTask,null);

        if (file.isPresent() && !file.get().isEmpty()) {
            if (document.getFileName() != null && document.getFileName().length() > 0)
                this.azureBlobAdapterService.deleteFile(document.getFileName());

            String fileName = this.azureBlobAdapterService.upload(file.get(), CommonUtil.getUniqueCode());
            document.setFileName(fileName);
            document.setUploadDate(CommonUtil.getDate());
        } else {
            Document findDocument = this.documentDao.findDocumentById(documentRequest.getId());
            document.setFileName(findDocument.getFileName());
        }

        document.setComplianceTask(complianceTask);
        Document updateDocument = this.documentDao.updateDocument(document);
        if (updateDocument == null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity fetchTaskDocument(Long id, Long taskId) {
        ComplianceTask complianceTask = this.complianceTaskDao.findComplianceTaskById(taskId);
        if (complianceTask == null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        Document document = this.documentDao.findDocumentByComplianceTaskAndId(complianceTask, id);
        if (document == null)
            return new ResponseEntity().badRequest("Document Not Found !!");

        return new ResponseEntity().ok(this.responseMapper.mapToDocumentResponse(document));
    }

    @Override
    public ResponseEntity deleteTaskDocument(Long id, Long taskId) {
        ComplianceTask complianceTask = this.complianceTaskDao.findComplianceTaskById(taskId);
        if (complianceTask == null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        Document document = this.documentDao.findDocumentByComplianceTaskAndId(complianceTask, id);
        if (document == null)
            return new ResponseEntity().badRequest("Document Not Found !!");

        boolean deleteDocument = this.documentDao.deleteDocument(document);
        if (!deleteDocument)
            return new ResponseEntity().internalServerError();

        if (document.getFileName() != null)
            this.azureBlobAdapterService.deleteFile(document.getFileName());

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity fetchAllSubTaskDocument(Long subTaskId) {
        ComplianceSubTask complianceSubTask = this.complianceSubTaskDao.findComplianceSubTaskById(subTaskId);
        if (complianceSubTask == null)
            return new ResponseEntity().badRequest("Compliance Sub-Task Not Found !!");

        List<Document> documentList = this.documentDao.findAllDocumentsByComplianceSubTask(complianceSubTask);
        if (documentList.isEmpty())
            return new ResponseEntity().noContent();

        return new ResponseEntity().ok(documentList.stream().map(d -> this.responseMapper.mapToDocumentResponse(d)));
    }

    @Override
    public ResponseEntity saveSubTaskDocument(DocumentRequest documentRequest, Optional<MultipartFile> file, Long subTaskId) {
        ComplianceSubTask complianceSubTask = this.complianceSubTaskDao.findComplianceSubTaskById(subTaskId);
        if (complianceSubTask == null)
            return new ResponseEntity().badRequest("Compliance Sub-Task Not Found !!");

        Document document = this.responseMapper.mapToSaveDocument(documentRequest,null,null,complianceSubTask);

        if (file.isPresent() && !file.get().isEmpty()) {
            String fileName = this.azureBlobAdapterService.upload(file.get(), CommonUtil.getUniqueCode());
            document.setFileName(fileName);
            document.setUploadDate(CommonUtil.getDate());
        }

        document.setComplianceSubTask(complianceSubTask);
        Document saveDocument = this.documentDao.saveDocument(document);
        if (saveDocument == null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity updateSubTaskDocument(DocumentRequest documentRequest, Optional<MultipartFile> file, Long subTaskId) {
        ComplianceSubTask complianceSubTask = this.complianceSubTaskDao.findComplianceSubTaskById(subTaskId);
        if (complianceSubTask == null)
            return new ResponseEntity().badRequest("Compliance Sub-Task Not Found !!");

        Document document = this.responseMapper.mapToUpdateDocument(documentRequest,
                null,null,complianceSubTask);

        if (file.isPresent() && !file.get().isEmpty()) {
            if (document.getFileName() != null && document.getFileName().length() > 0)
                this.azureBlobAdapterService.deleteFile(document.getFileName());

            String fileName = this.azureBlobAdapterService.upload(file.get(), CommonUtil.getUniqueCode());
            document.setFileName(fileName);
            document.setUploadDate(CommonUtil.getDate());
        } else {
            Document findDocument = this.documentDao.findDocumentById(documentRequest.getId());
            document.setFileName(findDocument.getFileName());
        }

        document.setComplianceSubTask(complianceSubTask);
        Document updateDocument = this.documentDao.updateDocument(document);
        if (updateDocument == null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity fetchSubTaskDocument(Long id, Long subTaskId) {
        ComplianceSubTask complianceSubTask = this.complianceSubTaskDao.findComplianceSubTaskById(subTaskId);
        if (complianceSubTask == null)
            return new ResponseEntity().badRequest("Compliance Sub-Task Not Found !!");

        Document document = this.documentDao.findDocumentByComplianceSubTaskAndId(complianceSubTask, id);
        if (document == null)
            return new ResponseEntity().badRequest("Document Not Found !!");

        return new ResponseEntity().ok(this.responseMapper.mapToDocumentResponse(document));
    }

    @Override
    public ResponseEntity deleteSubTaskDocument(Long id, Long subTaskId) {
        ComplianceSubTask complianceSubTask = this.complianceSubTaskDao.findComplianceSubTaskById(subTaskId);
        if (complianceSubTask == null)
            return new ResponseEntity().badRequest("Compliance Sub-Task Not Found !!");

        Document document = this.documentDao.findDocumentByComplianceSubTaskAndId(complianceSubTask, id);
        if (document == null)
            return new ResponseEntity().badRequest("Document Not Found !!");

        boolean deleteDocument = this.documentDao.deleteDocument(document);
        if (!deleteDocument)
            return new ResponseEntity().internalServerError();

        if (document.getFileName() != null)
            this.azureBlobAdapterService.deleteFile(document.getFileName());

        return new ResponseEntity().ok();
    }
}