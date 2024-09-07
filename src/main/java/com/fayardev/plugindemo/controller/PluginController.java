package com.fayardev.plugindemo.controller;

import com.fayardev.plugindemo.plugin.PluginContainer;
import com.fayardev.plugindemo.service.PluginService;
import com.fayardev.plugindemo.loader.utils.ZipExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/plugin")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PluginController {

    private final PluginService pluginService;

    @Autowired
    public PluginController(PluginService fileService) {
        this.pluginService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        pluginService.uploadPlugin(file);
        ZipExtractor.extract(file.getOriginalFilename());
        return ResponseEntity.ok("success");
    }

    @PostMapping("/load")
    public ResponseEntity<String> loadPlugin(@RequestBody String pluginName) {
        pluginService.load(pluginName);
        return ResponseEntity.ok("success");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deletePlugin(@RequestBody String pluginCode) {
        PluginContainer.getInstance().getClassRepository().delete(pluginCode);
        return null;
    }
}
