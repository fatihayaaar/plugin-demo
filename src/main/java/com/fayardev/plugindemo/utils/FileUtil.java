package com.fayardev.plugindemo.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

    public static boolean deleteDirectory(File directory) {
        if (!directory.exists()) {
            return true;
        }

        if (!directory.isDirectory()) {
            return false;
        }

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        return directory.delete();
    }

    public static void addFile(String filePath) {
        try {
            File file = new File(filePath);
            file.createNewFile();
        } catch (IOException e) {
          e.printStackTrace();
        }
    }

    public static void addDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public static void fileWriter(String filePath, String asset) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(readFileToString(asset));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFileToString(String filePath) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(filePath));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    public static void addFileContent(String filePath, String content) {
        try {
            File tempFile = File.createTempFile("tempfile", ".tmp");
            try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {
                writer.println(content);
                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.println(line);
                    }
                }
            }
            tempFile.renameTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
