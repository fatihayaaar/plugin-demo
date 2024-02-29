package com.fayardev.plugindemo.loader.packageloader;

import com.fayardev.plugindemo.loader.classloader.LibraryClassLoader;
import com.fayardev.plugindemo.loader.packageloader.abstracts.ILoader;
import com.fayardev.plugindemo.utils.ZipExtractor;

import java.io.File;
import java.net.URL;

public class BaseLoader implements ILoader {
    @Override
    public void loadPackage(String packageName) {
        LibraryClassLoader classLoader = new LibraryClassLoader(new URL[0]);
        String jarPath = ZipExtractor.EXTRACTED_DIR + File.separator + packageName;
        try {
            classLoader.loadAllClasses(jarPath);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
