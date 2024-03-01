package com.fayardev.plugindemo.loader.utils;

import java.io.*;
import java.nio.file.*;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.fayardev.plugindemo.loader.utils.RenamePluginDirectories.renamePluginDirectories;

public class TemplateRenamer {
    public static String rename() throws IOException {
        copyAndRenameZip("template/plugin55448867.zip", "template", "plugin44.zip");

        return "";
    }

    public static void copyAndRenameZip(String sourcePath, String destinationPath, String newFileName) {
        try {
            Path source = Paths.get(sourcePath);
            Path destination = Paths.get(destinationPath);

            String newFilePath = destination.resolve(newFileName).toString();

            Files.copy(source, Paths.get(newFilePath), StandardCopyOption.REPLACE_EXISTING);

            File newFile = new File(newFilePath);
            newFile.exists();

            Files.move(Paths.get(newFilePath), Paths.get(destinationPath, newFileName));
            unzip("template/plugin44.zip", "template/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry = zipIn.getNextEntry();
            while (entry != null) {
                String filePath = destDirectory + File.separator + entry.getName();
                if (!entry.getName().startsWith("__MACOSX") && !entry.getName().startsWith("._")) {
                    if (!entry.isDirectory()) {
                        extractFile(zipIn, filePath);
                    } else {
                        File dir = new File(filePath);
                        dir.mkdirs();
                    }
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        }
        renameFile("template/plugin55448867.jar", "template/plugin44.jar");
    }

    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = zipIn.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        }
    }

    public static void renameFile(String oldName, String newName) {
        File oldFile = new File(oldName);
        File newFile = new File(newName);
        oldFile.renameTo(newFile);
        renamePluginDirectories("template/plugin44.jar", "plugin55448867", "plugin44");
    }
}