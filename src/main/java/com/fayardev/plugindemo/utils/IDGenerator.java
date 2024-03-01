package com.fayardev.plugindemo.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IDGenerator {

    public static String generate() {
        return generateUniqueID();
    }

    private static String generateUniqueID() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        return now.format(formatter);
    }
}
