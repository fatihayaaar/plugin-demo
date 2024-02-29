package com.fayardev.plugindemo.controller;

import com.fayardev.plugindemo.dto.UserDto;
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

    private UserPluginAdapter userPluginAdapter;

    public void setup() {
        if (userPluginAdapter == null) {
            PluginContainer pluginContainer = PluginContainer.getInstance();
            try {
                userPluginAdapter = (UserPluginAdapter) pluginContainer.createPluginObject(PluginName.USER_PLUGIN);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody UserDto userDto) {
        setup();
        boolean confirm = userPluginAdapter.confirm(userDto.getUsername(), userDto.getPassword());
        if (confirm) {
            return ResponseEntity.ok("CONFIRM");
        }
        return ResponseEntity.ok("DENY");
    }
}
