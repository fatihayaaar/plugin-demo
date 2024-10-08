package com.fayardev.plugindemo.service;

import com.fayardev.plugindemo.loader.packageloader.LibraryLoader;
import com.fayardev.plugindemo.loader.packageloader.PluginLoader;
import com.fayardev.plugindemo.loader.packageloader.container.LoaderContainer;
import com.fayardev.plugindemo.loader.packageloader.container.LoaderName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class PluginService {

    private final FileService fileService;
    private PluginLoader pluginLoader;
    private LibraryLoader libraryLoader;

    @Autowired
    public PluginService(FileService fileService) {
        this.fileService = fileService;
        init();
    }

    private void init() {
        LoaderContainer loaderContainer = LoaderContainer.getInstance();
        pluginLoader = (PluginLoader) loaderContainer.getClassLoader(LoaderName.PLUGIN_LOADER);
        libraryLoader = (LibraryLoader) loaderContainer.getClassLoader(LoaderName.LIBRARY_LOADER);
    }

    public void uploadPlugin(MultipartFile file) throws IOException {
        fileService.uploadFile(file);
    }

    public void load(String pluginName) {
        libraryLoader.loadPackage(pluginName);
        pluginLoader.loadPackage(pluginName);
    }
}