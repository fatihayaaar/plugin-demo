package com.fayardev.plugindemo.entity;

import com.fayardev.plugindemo.plugin.PluginTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plugin {
    private String pluginName;
    private PluginTypeName pluginTypeName;
}
