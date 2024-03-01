package com.fayardev.plugindemo.controller;

import com.fayardev.plugindemo.service.PluginService;
import com.fayardev.plugindemo.loader.utils.ZipExtractor;
import com.fayardev.plugindemo.service.PluginTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/plugin")
public class PluginController {

    private final PluginService pluginService;

    @Autowired
    public PluginController(PluginService fileService) {
        this.pluginService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        pluginService.uploadPlugin(file);
        ZipExtractor.extract(file.getOriginalFilename());
        return ResponseEntity.ok("success");
    }

    @PostMapping("/load")
    public ResponseEntity<?> loadPlugin(@RequestBody String pluginName) {
        pluginService.load(pluginName);
        return ResponseEntity.ok("success");
    }
}
