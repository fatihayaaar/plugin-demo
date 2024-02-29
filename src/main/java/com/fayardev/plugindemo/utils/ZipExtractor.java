package com.fayardev.plugindemo.utils;

import com.fayardev.plugindemo.service.PluginService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipExtractor {

    public static String EXTRACTED_DIR = "uploads/extracted";

    public static void extract(String fileName) {
        String zipFilePath = PluginService.UPLOAD_DIR + File.separator + fileName;
        String destDir = EXTRACTED_DIR;

        try {
            unzip(zipFilePath, destDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void unzip(String zipFilePath, String destDir) throws IOException {
        File dir = new File(destDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        byte[] buffer = new byte[1024];
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                String fileName = zipEntry.getName();
                File newFile = new File(destDir + File.separator + fileName);
                new File(newFile.getParent()).mkdirs();
                if (!zipEntry.isDirectory()) {
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                } else {
                    newFile.mkdirs();
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        }
    }
}
