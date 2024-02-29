package com.fayardev.plugindemo.loader.utils;

import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipExtractor {
    private static String uploadPath = "uploads";
    public static final String EXTRACTED_DIRECTORY = "uploads/extracted";

    public static void extract(String fileName) {
        String zipFilePath = uploadPath + File.separator + fileName;
        try {
            unzip(zipFilePath, EXTRACTED_DIRECTORY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void unzip(String zipFilePath, String destDir) throws IOException {
        File dir = new File(destDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String zipFileName = new File(zipFilePath).getName();
        String newDestDir = destDir + File.separator + zipFileName.replace(".zip", "");
        dir = new File(newDestDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File pluginDir = new File(newDestDir + File.separator + "plugin");
        File libraryDir = new File(newDestDir + File.separator + "library");
        if (!pluginDir.exists()) {
            pluginDir.mkdirs();
        }
        if (!libraryDir.exists()) {
            libraryDir.mkdirs();
        }

        byte[] buffer = new byte[1024];
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                String fileName = zipEntry.getName();
                File newFile = new File(destDir + File.separator + zipEntry.getName());

                new File(newFile.getParent()).mkdirs();
                if (!zipEntry.isDirectory()) {
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }

                    if (fileName.startsWith("plugin")) {
                        moveFile(newFile.toPath(), new File(pluginDir + File.separator + newFile.getName()).toPath());
                    } else {
                        moveFile(newFile.toPath(), new File(libraryDir + File.separator + File.separator + newFile.getName()).toPath());
                    }
                } else {
                    newFile.mkdirs();
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        }
    }

    private static void moveFile(Path sourcePath, Path destPath) throws IOException {
        Files.move(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
    }
}
