package com.fayardev.plugindemo.utils;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Getter
@Setter
public class UnzipUtil implements Runnable {

    private static UnzipUtil instance;
    private String zipFilePath;
    private String destDirectory;

    private UnzipUtil() {
    }

    public static UnzipUtil getInstance() {
        if (instance == null)
            instance = new UnzipUtil();
        return instance;
    }

    @Override
    public void run() {
        unzip();
    }

    public void unzip() {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        byte[] buffer = new byte[1024];
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry zipEntry;
            try {
                zipEntry = zis.getNextEntry();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            while (zipEntry != null) {
                File newFile = newFile(destDirectory, zipEntry);
                if (zipEntry.isDirectory()) {
                    newFile.mkdirs();
                } else {
                    File parent = newFile.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private File newFile(String destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());
        String destDirPath = new File(destinationDir).getCanonicalPath();
        String destFileCanonicalPath = destFile.getCanonicalPath();
        if (!destFileCanonicalPath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target directory: " + zipEntry.getName());
        }
        return destFile;
    }
}
