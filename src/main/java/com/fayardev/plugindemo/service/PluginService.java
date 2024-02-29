package com.fayardev.plugindemo.service;

import com.fayardev.plugindemo.loader.packageloader.LibraryLoader;
import com.fayardev.plugindemo.loader.packageloader.PluginLoader;
import com.fayardev.plugindemo.loader.packageloader.container.LoaderContainer;
import com.fayardev.plugindemo.loader.packageloader.container.LoaderName;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class PluginService {

    public static String UPLOAD_DIR = "uploads";

    public void uploadPlugin(MultipartFile file) throws IOException {
        String path = UPLOAD_DIR + File.separator + file.getOriginalFilename();
        File targetFile = new File(path);
        try (OutputStream fileOutputStream = new FileOutputStream(targetFile)) {
            FileCopyUtils.copy(file.getInputStream(), fileOutputStream);
        }
        //removeClassFromJar(path, "com/fatihayar/plugindemo/plugin/adapter/UserPluginAdapter.class");
    }

    public void load(String pluginName) {
        loadLibrary(pluginName);
        loadPlugin(pluginName);
    }

    private void loadPlugin(String pluginName) {
        PluginLoader pluginLoader = (PluginLoader) LoaderContainer.getInstance().getClassLoader(LoaderName.PLUGIN_LOADER);
        pluginLoader.loadPackage(pluginName);
    }

    private void loadLibrary(String pluginName) {
        LibraryLoader libraryLoader = (LibraryLoader) LoaderContainer.getInstance().getClassLoader(LoaderName.LIBRARY_LOADER);
        libraryLoader.loadPackage(pluginName);
    }
}