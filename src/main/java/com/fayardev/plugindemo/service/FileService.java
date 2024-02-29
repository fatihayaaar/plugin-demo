package com.fayardev.plugindemo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class FileService {

    @Value("${upload.path}")
    private String uploadDirectory;

    public void uploadFile(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            try {
                byte[] bytes = file.getBytes();
                File uploadedFile = new File(uploadDirectory + File.separator + fileName);
                try (FileOutputStream fos = new FileOutputStream(uploadedFile)) {
                    fos.write(bytes);
                }
            } catch (IOException e) {
                throw new IOException(e.getMessage());
            }
        } else {
            throw new IOException();
        }
    }

    public File getFileByPath(String filePath) {
        File file = new File(filePath);

        if (file.exists()) {
            return file;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
