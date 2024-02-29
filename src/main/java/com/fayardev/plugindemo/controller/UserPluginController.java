package com.fayardev.plugindemo.controller;

import com.fayardev.plugindemo.dto.UserDto;
import com.fayardev.plugindemo.loader.packageloader.PluginLoader;
import com.fayardev.plugindemo.loader.packageloader.container.LoaderContainer;
import com.fayardev.plugindemo.loader.packageloader.container.LoaderName;
import com.fayardev.plugindemo.plugin.PluginContainer;
import com.fayardev.plugindemo.plugin.PluginName;
import com.fayardev.plugindemo.plugin.adapter.UserPluginAdapter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("/api/plugin/user")
public class UserPluginController {

    private PluginContainer pluginContainer;
    private UserPluginAdapter userPluginAdapter;

    public UserPluginController() {
        init();
    }

    private void init() {
        pluginContainer = PluginContainer.getInstance();
        try {
            userPluginAdapter = (UserPluginAdapter) pluginContainer.createPluginObject(PluginName.USER_PLUGIN);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userPluginAdapter.confirm(userDto.getUsername(), userDto.getPassword()));
    }
}
