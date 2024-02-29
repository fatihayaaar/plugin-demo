package com.fayardev.plugindemo.loader.packageloader;

import com.fayardev.plugindemo.loader.classloader.LibraryClassLoader;
import com.fayardev.plugindemo.loader.utils.ZipExtractor;

import java.io.File;
import java.net.URL;
import java.util.List;

public class LibraryLoader extends BaseLoader {

    @Override
    public void loadPackage(String packageName) {
        LibraryClassLoader classLoader = new LibraryClassLoader(new URL[0]);
        String jarPath = ZipExtractor.EXTRACTED_DIR + File.separator + packageName + File.separator + "library";
        List<String> jarsName = super.getJarsName(jarPath);
        for (String jarName : jarsName) {
            try {
                classLoader.loadAllClasses(jarPath + File.separator + jarName);
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }
}
