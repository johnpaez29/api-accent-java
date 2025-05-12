package com.project.agentic_ai_api.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;

@Service
public class FileService {
      private final BlobContainerClient containerClient;


    public FileService(
        @Value("${azure.storage.connection-string}") String connectionString,
        @Value("${azure.storage.container-name}") String containerName
    ) {
        this.containerClient = new BlobServiceClientBuilder()
            .connectionString(connectionString)
            .buildClient()
            .getBlobContainerClient(containerName);

        if (!containerClient.exists()) {
            containerClient.create();
        }
    }

    public String upload(MultipartFile file) throws IOException {
        BlobClient blobClient = containerClient.getBlobClient(file.getOriginalFilename());
        blobClient.upload(file.getInputStream(), file.getSize(), true);
        blobClient.setHttpHeaders(new BlobHttpHeaders().setContentType(file.getContentType()));
        return blobClient.getBlobUrl();
    }

    public byte[] download(String filename) throws IOException {
        BlobClient blobClient = containerClient.getBlobClient(filename);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        blobClient.downloadStream(outputStream);
        return outputStream.toByteArray();
    }

    public void delete(String filename) {
        containerClient.getBlobClient(filename).delete();
    }
}