package com.fayardev.plugindemo.controller;

import com.fayardev.plugindemo.dto.PluginTemplateDto;
import com.fayardev.plugindemo.service.PluginTemplateService;
import com.fayardev.plugindemo.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/plugin-template")
@RequiredArgsConstructor
public class PluginTemplateController {

    private final PluginTemplateService service;

    @PostMapping("/build")
    public ResponseEntity<byte[]> getTemplate(@RequestBody PluginTemplateDto pluginTemplateDto) throws IOException, InterruptedException {
        File file = service.templateBuild(pluginTemplateDto.getPluginTypeNames());

        byte[] fileContent = FileCopyUtils.copyToByteArray(file);
        String fileName = file.getName();

        file.delete();

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + fileName)
                .body(fileContent);
    }
}
