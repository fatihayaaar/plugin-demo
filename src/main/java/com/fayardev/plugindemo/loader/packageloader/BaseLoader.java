package com.fayardev.plugindemo.loader.packageloader;

import com.fayardev.plugindemo.loader.classloader.LibraryClassLoader;
import com.fayardev.plugindemo.loader.packageloader.abstracts.ILoader;
import com.fayardev.plugindemo.loader.repository.ClassRepository;
import com.fayardev.plugindemo.loader.utils.ZipExtractor;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BaseLoader implements ILoader {

    protected final ClassRepository repository;

    public BaseLoader() {
        repository = new ClassRepository();
    }

    @Override
    public void loadPackage(String packageName) {
        LibraryClassLoader classLoader = new LibraryClassLoader(new URL[0]);
        try {
            classLoader.loadAllClasses(ZipExtractor.pluginsPath + File.separator + packageName);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    protected List<String> getJarsName(String path) {
        File dir = new File(path);
        File[] files = dir.listFiles();

        List<String> jarFiles = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".jar")) {
                    jarFiles.add(file.getName());
                }
            }
        }
        return jarFiles;
    }
}
