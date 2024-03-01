package com.fayardev.plugindemo.loader.utils;

import java.io.*;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class RenamePluginDirectories {

    public static void renamePluginDirectories(String jarFilePath, String directoryName, String newDirectoryName) {
        try {
            File tempDir = new File("temp_extracted");
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }

            JarFile jarFile = new JarFile(jarFilePath);
            Enumeration<JarEntry> entries = jarFile.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                if (name.startsWith(directoryName + "/") && entry.isDirectory()) {
                    name = name.replace(directoryName, newDirectoryName);
                }
                File destFile = new File(tempDir, name);
                if (entry.isDirectory()) {
                    destFile.mkdirs();
                } else {
                    destFile.getParentFile().mkdirs();
                    InputStream in = jarFile.getInputStream(entry);
                    OutputStream out = new FileOutputStream(destFile);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0) {
                        out.write(buffer, 0, length);
                    }
                    in.close();
                    out.close();
                }
            }
            jarFile.close();

            // Create a new JAR file with modified entries
            createNewJar(jarFilePath, tempDir);

            // Delete temporary directory
            deleteDirectory(tempDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createNewJar(String originalJarPath, File tempDir) {
        try {
            FileOutputStream fos = new FileOutputStream("new_file_name.jar");
            JarOutputStream jos = new JarOutputStream(fos);
            addToJar(tempDir, tempDir, jos);
            jos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addToJar(File root, File source, JarOutputStream target) throws IOException {
        BufferedInputStream in = null;
        try {
            String name = source.getPath().substring(root.getPath().length() + 1);
            if (source.isDirectory()) {
                if (!name.isEmpty()) {
                    if (!name.endsWith("/")) {
                        name += "/";
                    }
                    JarEntry entry = new JarEntry(name);
                    entry.setTime(source.lastModified());
                    target.putNextEntry(entry);
                    target.closeEntry();
                }
                for (File nestedFile : source.listFiles()) {
                    addToJar(root, nestedFile, target);
                }
                return;
            }

            JarEntry entry = new JarEntry(name);
            entry.setTime(source.lastModified());
            target.putNextEntry(entry);
            in = new BufferedInputStream(new FileInputStream(source));

            byte[] buffer = new byte[1024];
            while (true) {
                int count = in.read(buffer);
                if (count == -1) break;
                target.write(buffer, 0, count);
            }
            target.closeEntry();
        } finally {
            if (in != null) in.close();
        }
    }

    public static void deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDirectory(file);
                }
            }
        }
        directory.delete();
    }
}
