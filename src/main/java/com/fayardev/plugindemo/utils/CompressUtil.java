package com.fayardev.plugindemo.utils;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Getter
@Setter
public class CompressUtil implements Runnable {

    private static CompressUtil instance;
    private String sourceDirPath;
    private String zipFilePath;

    private CompressUtil() {
    }

    public static CompressUtil getInstance() {
        if (instance == null)
            instance = new CompressUtil();
        return instance;
    }

    @Override
    public void run() {
        compress();
    }

    public void compress() {
        try {
            FileOutputStream fos = new FileOutputStream(zipFilePath);
            ZipOutputStream zos = new ZipOutputStream(fos);
            File sourceDir = new File(sourceDirPath);
            addDirToZipArchive(zos, sourceDir, sourceDir);
            zos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addDirToZipArchive(ZipOutputStream zos, File fileToZip, File rootDir) throws IOException {
        for (File file : fileToZip.listFiles()) {
            if (file.isDirectory()) {
                addDirToZipArchive(zos, file, rootDir);
                continue;
            }
            FileInputStream fis = new FileInputStream(file);
            String zipFilePath = file.getCanonicalPath().substring(rootDir.getCanonicalPath().length() + 1);
            ZipEntry zipEntry = new ZipEntry(zipFilePath);
            zos.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }
            fis.close();
        }
    }
}
