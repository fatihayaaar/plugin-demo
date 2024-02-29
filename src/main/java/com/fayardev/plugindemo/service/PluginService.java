package com.fayardev.plugindemo.service;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class PluginService {

    public static String UPLOAD_DIR = "uploads";

    public void uploadPlugin(MultipartFile file) throws IOException {
        String path = UPLOAD_DIR + File.separator + file.getOriginalFilename();
        File targetFile = new File(path);
        try (OutputStream fileOutputStream = new FileOutputStream(targetFile)) {
            FileCopyUtils.copy(file.getInputStream(), fileOutputStream);
        }
        //removeClassFromJar(path, "com/fatihayar/plugindemo/plugin/adapter/UserPluginAdapter.class");
    }

    public void load(String pluginName) {

    }
}