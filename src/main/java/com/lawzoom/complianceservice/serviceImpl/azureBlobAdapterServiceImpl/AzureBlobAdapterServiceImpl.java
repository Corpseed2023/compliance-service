package com.lawzoom.complianceservice.serviceImpl.azureBlobAdapterServiceImpl;

import com.azure.storage.blob.BlobClientBuilder;
import com.lawzoom.complianceservice.service.azureBlobAdapterService.AzureBlobAdapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class AzureBlobAdapterServiceImpl implements AzureBlobAdapterService {

    @Autowired
    private BlobClientBuilder client;

    @Override
    public String upload(MultipartFile file, long prefixName) {
        String fileName=null;
        if(file != null && file.getSize() > 0) {
            try {
                //implement your own file name logic.
                if(prefixName!=0)
                    fileName = prefixName+file.getOriginalFilename().replace(" ", "_");

                else fileName=file.getOriginalFilename().replace(" ", "_");
                boolean fileExist = isFileExist(fileName);
//            	BlobHttpHeaders headers = new BlobHttpHeaders().setContentType("image/svg+xml");
//                BlobAsyncClient buildAsyncClient = client.buildAsyncClient();
//                buildAsyncClient.setHttpHeaders(headers);
//                buildAsyncClient.uploadFromFile(file.getResource().getFile().getAbsolutePath());

                if(!fileExist)
                    client.blobName(fileName).buildClient().upload(file.getInputStream(),file.getSize());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return fileName;
    }

    public byte[] getFile(String name) {
        try {
            File temp = new File("/temp/"+name);
//            BlobProperties properties = client.blobName(name).buildClient().downloadToFile(temp.getPath());
            byte[] content = Files.readAllBytes(Paths.get(temp.getPath()));
            temp.delete();
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteFile(String name) {
        try {
            if(client.blobName(name).buildClient().exists())
                client.blobName(name).buildClient().delete();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    public boolean isFileExist(String name) {
        boolean flag=false;
        try {
            if(client.blobName(name).buildClient().exists())
                flag=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
