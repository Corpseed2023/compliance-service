package com.lawzoom.complianceservice.service.azureBlobAdapterService;

import org.springframework.web.multipart.MultipartFile;

public interface AzureBlobAdapterService {

    String upload(MultipartFile multipartFile, long uniqueCode);

    boolean deleteFile(String fileName);

    boolean isFileExist(String name);

    byte[] getFile(String name);
}
