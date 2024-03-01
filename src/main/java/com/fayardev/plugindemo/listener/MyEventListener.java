package com.fayardev.plugindemo.listener;

import com.fayardev.plugindemo.loader.utils.DirectoryLister;
import com.fayardev.plugindemo.service.PluginService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyEventListener {

    private final PluginService pluginService;

    @Value("${plugin.path}")
    private String pluginsPath;

    public MyEventListener(PluginService pluginService) {
        this.pluginService = pluginService;
    }

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        loadPlugins();
    }

    private void loadPlugins() {
        List<String> plugins = DirectoryLister.list(pluginsPath);
        plugins.forEach(pluginService::load);
    }
}
