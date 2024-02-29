package com.fayardev.plugindemo.loader.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;

public class RemoveClass {

    public static void removeClassFromJar(String jarPath, String classPath) {
        try {
            File jarFile = new File(jarPath);
            File tempFile = new File(jarPath + ".tmp");

            JarInputStream jarIn = new JarInputStream(new FileInputStream(jarFile));
            JarOutputStream jarOut = new JarOutputStream(new FileOutputStream(tempFile));

            JarEntry entry;
            while ((entry = jarIn.getNextJarEntry()) != null) {
                if (!entry.getName().equals(classPath)) {
                    jarOut.putNextEntry(new JarEntry(entry.getName()));
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = jarIn.read(buffer)) != -1) {
                        jarOut.write(buffer, 0, bytesRead);
                    }
                }
            }

            jarIn.close();
            jarOut.close();

            if (!jarFile.delete()) {
                throw new IOException("Failed to delete original JAR file");
            }

            if (!tempFile.renameTo(jarFile)) {
                throw new IOException("Failed to rename temporary JAR file");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
