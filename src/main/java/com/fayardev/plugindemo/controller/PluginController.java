package com.fayardev.plugindemo.controller;

import com.fayardev.plugindemo.loader.packageloader.PluginLoader;
import com.fayardev.plugindemo.loader.packageloader.container.LoaderContainer;
import com.fayardev.plugindemo.loader.packageloader.container.LoaderName;
import com.fayardev.plugindemo.service.PluginService;
import com.fayardev.plugindemo.utils.ZipExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/plugin")
public class PluginController {

    private final PluginService fileService;

    @Autowired
    public PluginController(PluginService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        fileService.uploadPlugin(file);
        ZipExtractor.extract(file.getOriginalFilename());
        return "success";
    }

    @PostMapping("/load")
    public ResponseEntity<?> loadPlugin(@RequestBody String pluginName) {
        PluginLoader pluginLoader = (PluginLoader) LoaderContainer.getInstance().getClassLoader(LoaderName.PLUGIN_LOADER);
        pluginLoader.loadPackage(pluginName);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/template")
    public ResponseEntity<?> getTemplate() {
        return null;
    }
}
