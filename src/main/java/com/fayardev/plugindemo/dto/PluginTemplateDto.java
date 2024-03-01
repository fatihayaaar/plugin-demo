package com.fayardev.plugindemo.dto;

import com.fayardev.plugindemo.plugin.PluginTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PluginTemplateDto {

    private List<PluginTypeName> pluginTypeNames;
}
