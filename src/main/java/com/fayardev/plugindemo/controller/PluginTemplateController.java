package com.fayardev.plugindemo.controller;

import com.fayardev.plugindemo.service.PluginTemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/plugin-template")
public class PluginTemplateController {

    private PluginTemplateService service;

    public PluginTemplateController(PluginTemplateService service) {
        this.service = service;
    }

    @GetMapping("/build")
    public ResponseEntity<byte[]> getTemplate() throws IOException, InterruptedException {
        File file = service.templateBuild();
        byte[] fileContent = FileCopyUtils.copyToByteArray(file);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + file.getName())
                .body(fileContent);
    }
}
