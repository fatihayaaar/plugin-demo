package com.fayardev.plugindemo.controller;

import com.fayardev.plugindemo.dto.UserDto;
import com.fayardev.plugindemo.plugin.PluginContainer;
import com.fayardev.plugindemo.plugin.PluginTypeName;
import com.fayardev.plugindemo.plugin.adapter.UserPluginAdapter;
import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("/api/plugin/user")
public class UserPluginController {

    private UserPluginAdapter userPluginAdapter;
    private PluginContainer pluginContainer;

    @PostConstruct
    private void init() {
        pluginContainer = PluginContainer.getInstance();
    }

    public void setup(String pluginName) {
        pluginContainer = PluginContainer.getInstance();
        try {
            userPluginAdapter = (UserPluginAdapter) pluginContainer.createPluginObject(pluginName, PluginTypeName.USER_PLUGIN);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/{plugin-name}/verify")
    public ResponseEntity<String> verify(@PathVariable("plugin-name") String pluginName, @RequestBody UserDto userDto) {
        setup(pluginName);
        boolean confirm = userPluginAdapter.confirm(userDto.getUsername(), userDto.getPassword());
        if (confirm) {
            return ResponseEntity.ok("CONFIRM");
        }
        return ResponseEntity.ok("DENY");
    }
}
