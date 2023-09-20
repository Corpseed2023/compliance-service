package com.lawzoom.complianceservice.serviceImpl.documentServiceImpl;


import com.lawzoom.complianceservice.dto.documentDto.DocumentRequest;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.complianceSubTaskModel.ComplianceSubTask;
import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
import com.lawzoom.complianceservice.model.documentModel.Document;
import com.lawzoom.complianceservice.repository.ComplianceRepo;
import com.lawzoom.complianceservice.repository.ComplianceSubTaskRepository;
import com.lawzoom.complianceservice.repository.ComplianceTaskRepository;
import com.lawzoom.complianceservice.repository.DocumentRepository;
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


    private ComplianceTaskRepository complianceTaskRepository;

    private ComplianceSubTaskRepository complianceSubTaskRepository;

    private ComplianceRepo complianceRepository;

    private DocumentRepository documentRepository;


    @Autowired
    private ResponseMapper responseMapper;

    @Autowired
    private AzureBlobAdapterService azureBlobAdapterService;

    @Override
    @Override
    public ResponseEntity fetchAllComplianceDocument(Long complianceId) {
        Compliance compliance = this.complianceRepository.findComplianceById(complianceId);
        if (compliance == null)
            new ResponseEntity().badRequest("Compliance Not Found !!");

        List<Document> documentList = this.documentRepository.findAllDocumentsByCompliance(compliance);
        if (documentList.isEmpty())
            return ResponseEntity.noContent().build();

        return new ResponseEntity.ok(documentList.stream().map(d -> this.responseMapper.mapToDocumentResponse(d)));
    }
    @Override
    public ResponseEntity saveComplianceDocument(DocumentRequest documentRequest, Optional<MultipartFile> file, Long complianceId) {
        Compliance compliance = this.complianceRepository.findComplianceById(complianceId);
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
        Document saveDocument = this.documentRepository.save(document);
        if (saveDocument == null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity updateComplianceDocument(DocumentRequest documentRequest, Optional<MultipartFile> file, Long complianceId) {
        Compliance compliance = this.complianceRepository.findComplianceById(complianceId);
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
            Document findDocument = this.documentRepository.findDocumentById(documentRequest.getId());
            document.setFileName(findDocument.getFileName());
        }


        document.setCompliance(compliance);
        Document updateDocument = this.documentRepository.save(document);
        if (updateDocument == null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
//    public ResponseEntity fetchComplianceDocument(Long id, Long complianceId) {
//        Compliance compliance = this.complianceRepository.findComplianceById(complianceId);
//        if (compliance == null)
//            return new ResponseEntity().badRequest("Compliance Not Found !!");
//
//        Document document = this.documentRepository.findDocumentByComplianceAndId(compliance, id);
//        if (document == null)
//            return new ResponseEntity().badRequest("Document Not Found !!");
//
//        return new ResponseEntity().ok(this.responseMapper.mapToDocumentResponse(document));
//    }


    public ResponseEntity fetchComplianceDocument(Long id, Long complianceId) {
        Compliance compliance = this.complianceRepository.findComplianceById(complianceId);
        if (compliance == null)
            return new ResponseEntity().badRequest("Compliance Not Found !!");

        Document document = this.documentRepository.findDocumentByComplianceAndId(compliance, id);
        if (document == null)
            return new ResponseEntity().badRequest("Document Not Found !!");

//        return ResponseEntity.ok(this.responseMapper.mapToDocumentResponse(document));
        return new ResponseEntity().ok(this.responseMapper.mapToDocumentResponse(document));

    }


    @Override
    public ResponseEntity deleteComplianceDocument(Long id, Long complianceId) {
        Compliance compliance = this.complianceRepository.findComplianceById(complianceId);
        if (compliance == null)
//            return ResponseEntity.badRequest().body("Compliance Not Found !!");
            return new ResponseEntity().badRequest("Compliance Not Found !!");

        Document document = this.documentRepository.findDocumentByComplianceAndId(compliance, id);
        if (document == null)
//            return ResponseEntity.badRequest().body("Document Not Found !!");
            return new ResponseEntity().badRequest("Document Not Found !!");


        try {
            this.documentRepository.delete(document);

            if (document.getFileName() != null)
                this.azureBlobAdapterService.deleteFile(document.getFileName());

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound();
        }
    }


    @Override
    public ResponseEntity fetchAllTaskDocument(Long taskId) {
        ComplianceTask complianceTask = this.complianceTaskRepository.findComplianceTaskById(taskId);
        if (complianceTask == null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        List<Document> documentList = this.documentRepository.findAllDocumentsByComplianceTask(complianceTask);
        if (documentList.isEmpty())
            return new ResponseEntity().noContent();

        return new ResponseEntity().ok(documentList.stream().map(d -> this.responseMapper.mapToDocumentResponse(d)));
    }


    @Override
    public ResponseEntity saveTaskDocument(DocumentRequest documentRequest, Optional<MultipartFile> file, Long taskId) {
        ComplianceTask complianceTask = this.complianceTaskRepository.findComplianceTaskById(taskId);
        if (complianceTask == null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        Document document = this.responseMapper.mapToSaveDocument(documentRequest,null,complianceTask,null);

        if (file.isPresent() && !file.get().isEmpty()) {
            String fileName = this.azureBlobAdapterService.upload(file.get(), CommonUtil.getUniqueCode());
            document.setFileName(fileName);
            document.setUploadDate(CommonUtil.getDate());
        }

        document.setComplianceTask(complianceTask);
        Document saveDocument = this.documentRepository.save(document);
        if (saveDocument == null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity updateTaskDocument(DocumentRequest documentRequest, Optional<MultipartFile> file, Long taskId) {
        ComplianceTask complianceTask = complianceTaskRepository.findComplianceTaskById(taskId);
        if (complianceTask == null)
//            return new ResponseEntity().badRequest().body("Compliance Task Not Found !!");
      return new  ResponseEntity().badRequest("Compliance Task Not Found !!");

        Optional<Document> optionalDocument = documentRepository.findById(documentRequest.getId());
        if (!optionalDocument.isPresent())
            return new ResponseEntity().badRequest("Document Not Found !!");

        Document alreadyDocument = optionalDocument.get();

        Document updatedDocument = responseMapper.mapToUpdateDocument(documentRequest, alreadyDocument.getCompliance(), complianceTask, null);

        if (file.isPresent() && !file.get().isEmpty()) {
            if (alreadyDocument.getFileName() != null && alreadyDocument.getFileName().length() > 0) {
                azureBlobAdapterService.deleteFile(alreadyDocument.getFileName());
            }

            String fileName = azureBlobAdapterService.upload(file.get(), CommonUtil.getUniqueCode());
            updatedDocument.setFileName(fileName);
            updatedDocument.setUploadDate(CommonUtil.getDate());
        } else {
            updatedDocument.setFileName(alreadyDocument.getFileName());
        }

        updatedDocument.setComplianceTask(complianceTask);
        Document savedDocument = documentRepository.save(updatedDocument);

        if (savedDocument == null)
            return new ResponseEntity().badRequest("Failed to update document.");

        return ResponseEntity.updatedStatus("Document updated successfully.");
    }

    @Override
    public ResponseEntity fetchTaskDocument(Long id, Long taskId) {
        ComplianceTask complianceTask = this.complianceTaskRepository.findComplianceTaskById(taskId);
        if (complianceTask == null)
            return new ResponseEntity().badRequest("Compliance Task Not Found !!");

        Document document = this.documentRepository.findDocumentByComplianceTaskAndId(complianceTask, id);
        if (document == null)
            return new ResponseEntity().badRequest("Document Not Found !!");

        return new ResponseEntity().ok(this.responseMapper.mapToDocumentResponse(document));
    }

//    @Override
//    public ResponseEntity deleteTaskDocument(Long id, Long taskId) {
//        ComplianceTask complianceTask = this.complianceTaskRepository.findComplianceTaskById(taskId);
//        if (complianceTask == null)
//            return new ResponseEntity().badRequest("Compliance Task Not Found !!");
//
//        Document document = this.documentRepository.findDocumentByComplianceTaskAndId(complianceTask, id);
//        if (document == null)
//            return new ResponseEntity().badRequest("Document Not Found !!");
//
//        boolean deleteDocument = this.documentRepository.deleteDocument(document);
//        if (!deleteDocument)
//            return new ResponseEntity().internalServerError();
//
//        if (document.getFileName() != null)
//            this.azureBlobAdapterService.deleteFile(document.getFileName());
//
//        return new ResponseEntity().ok();



        @Override
        public ResponseEntity deleteTaskDocument(Long id, Long taskId) {
            ComplianceTask complianceTask = complianceTaskRepository.findComplianceTaskById(taskId);
            if (complianceTask == null)
                return new ResponseEntity().badRequest("Compliance Task Not Found !!");

            boolean documentExists = documentRepository.existsByIdAndComplianceTask(id, complianceTask);
            if (!documentExists)
                return new ResponseEntity().badRequest("Document Not Found !!");

            documentRepository.deleteDocumentByIdAndComplianceTask(id, complianceTask);


            return new ResponseEntity().successfullStatus("Document deleted successfully.");
        }


    @Override
    public ResponseEntity fetchAllSubTaskDocument(Long subTaskId) {
        ComplianceSubTask complianceSubTask = this.complianceSubTaskRepository.findComplianceSubTaskById(subTaskId);
        if (complianceSubTask == null)
            return new ResponseEntity().badRequest("Compliance Sub-Task Not Found !!");

        List<Document> documentList = this.documentRepository.findAllByComplianceSubTask(complianceSubTask);
        if (documentList.isEmpty())
            return new ResponseEntity().noContent();

        return new ResponseEntity().ok(documentList.stream().map(d -> this.responseMapper.mapToDocumentResponse(d)));
    }



    @Override
    public ResponseEntity saveSubTaskDocument(DocumentRequest documentRequest, Optional<MultipartFile> file, Long subTaskId) {
        ComplianceSubTask complianceSubTask = this.complianceSubTaskRepository.findComplianceSubTaskById(subTaskId);
        if (complianceSubTask == null)
            return new ResponseEntity().badRequest("Compliance Sub-Task Not Found !!");

        Document document = this.responseMapper.mapToSaveDocument(documentRequest,null,null,complianceSubTask);

        if (file.isPresent() && !file.get().isEmpty()) {
            String fileName = this.azureBlobAdapterService.upload(file.get(), CommonUtil.getUniqueCode());
            document.setFileName(fileName);
            document.setUploadDate(CommonUtil.getDate());
        }

        document.setComplianceSubTask(complianceSubTask);
        Document saveDocument = this.documentRepository.save(document);
        if (saveDocument == null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity updateSubTaskDocument(DocumentRequest documentRequest, Optional<MultipartFile> file, Long subTaskId) {
        ComplianceSubTask complianceSubTask = this.complianceSubTaskRepository.findComplianceSubTaskById(subTaskId);
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
            Document findDocument = this.documentRepository.findById(documentRequest.getId()).orElse(null);
            if (findDocument != null) {
                document.setFileName(findDocument.getFileName());
            }
        }

        document.setComplianceSubTask(complianceSubTask);
        Document updateDocument = this.documentRepository.save(document);

        if (updateDocument == null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }


    @Override
    public ResponseEntity fetchSubTaskDocument(Long id, Long subTaskId) {
        ComplianceSubTask complianceSubTask = this.complianceSubTaskRepository.findComplianceSubTaskById(subTaskId);
        if (complianceSubTask == null)
            return new ResponseEntity().badRequest("Compliance Sub-Task Not Found !!");

//        Document document = this.documentRepository.findDocumentByComplianceSubTaskAndId(complianceSubTask, id);
          Document document = this.documentRepository.findDocumentByComplianceSubTaskAndId(complianceSubTask, id);


        if (document == null)
            return new ResponseEntity().badRequest("Document Not Found !!");

        return new ResponseEntity().ok(this.responseMapper.mapToDocumentResponse(document));
    }

    @Override
    public ResponseEntity deleteSubTaskDocument(Long id, Long subTaskId) {
        ComplianceSubTask complianceSubTask = this.complianceSubTaskRepository.findComplianceSubTaskById(subTaskId);
        if (complianceSubTask == null)
            return new ResponseEntity().badRequest("Compliance Sub-Task Not Found !!");

        Document document = this.documentRepository.findDocumentByComplianceSubTaskAndId(complianceSubTask, id);
        if (document == null)
            return new ResponseEntity().badRequest("Document Not Found !!");

        this.documentRepository.delete(document);

        if (document.getFileName() != null)
            this.azureBlobAdapterService.deleteFile(document.getFileName());

        return ResponseEntity.ok().build();
    }

}