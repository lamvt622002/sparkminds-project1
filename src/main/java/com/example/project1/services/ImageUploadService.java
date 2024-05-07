package com.example.project1.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface ImageUploadService {
    File convertToFile(MultipartFile multipartFile, String fileName)throws IOException;

    String uploadFile(File file, String fileName)throws IOException;

    String upload(MultipartFile multipartFile);

    String uploadFileToServer(MultipartFile multipartFile) throws IOException;

    void validateImageFile(MultipartFile multipartFile);
}
