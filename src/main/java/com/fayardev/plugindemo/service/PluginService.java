package com.fayardev.plugindemo.service;

import com.fayardev.plugindemo.dto.PluginDto;
import com.fayardev.plugindemo.entity.Plugin;
import com.fayardev.plugindemo.loader.packageloader.LibraryLoader;
import com.fayardev.plugindemo.loader.packageloader.PluginLoader;
import com.fayardev.plugindemo.loader.packageloader.container.LoaderContainer;
import com.fayardev.plugindemo.loader.packageloader.container.LoaderName;
import com.fayardev.plugindemo.repository.PluginRepository;
import com.fayardev.plugindemo.utils.FileUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class PluginService {

    private final PluginRepository repository;
    private final FileService fileService;
    private final ModelMapper modelMapper;
    private PluginLoader pluginLoader;
    private LibraryLoader libraryLoader;

    @Autowired
    public PluginService(PluginRepository repository, FileService fileService, ModelMapper modelMapper) {
        this.repository = repository;
        this.fileService = fileService;
        this.modelMapper = modelMapper;

        init();
    }

    private void init() {
        LoaderContainer loaderContainer = LoaderContainer.getInstance();
        pluginLoader = (PluginLoader) loaderContainer.getClassLoader(LoaderName.PLUGIN_LOADER);
        libraryLoader = (LibraryLoader) loaderContainer.getClassLoader(LoaderName.LIBRARY_LOADER);
    }

    public void uploadPlugin(MultipartFile file, String pluginName) throws IOException {
        String pluginCode = FileUtil.getFileNameWithoutExtension(file.getOriginalFilename());
        PluginDto pluginDto = PluginDto.builder().pluginCode(pluginCode).pluginName(pluginName).build();
        add(pluginDto);
        fileService.uploadFile(file);
    }

    public void load(String pluginName) {
        libraryLoader.loadPackage(pluginName);
        pluginLoader.loadPackage(pluginName);
    }

    public void add(PluginDto pluginDto) {
        repository.saveAndFlush(modelMapper.map(pluginDto, Plugin.class));
    }

    public void update(PluginDto pluginDto) {
        PluginDto pluginDto1 = findByPluginCode(pluginDto.getPluginCode());
        pluginDto1.setPluginName(pluginDto.getPluginName());
        repository.saveAndFlush(modelMapper.map(pluginDto1, Plugin.class));
    }

    public PluginDto findByPluginCode(String pluginCode) {
        return modelMapper.map(repository.findByPluginCode(pluginCode).orElseThrow(IllegalAccessError::new), PluginDto.class);
    }

    public boolean delete(String pluginCode) {
        return repository.deleteByPluginCode(pluginCode);
    }
}