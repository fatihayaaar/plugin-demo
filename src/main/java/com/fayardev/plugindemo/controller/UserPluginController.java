package com.fayardev.plugindemo.controller;

import com.fayardev.plugindemo.loader.packageloader.PluginLoader;
import com.fayardev.plugindemo.loader.packageloader.container.LoaderContainer;
import com.fayardev.plugindemo.loader.packageloader.container.LoaderName;
import com.fayardev.plugindemo.plugin.adapter.UserPluginAdapter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plugin/user")
public class UserPluginController {

    UserPluginAdapter userPlugin;

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody String username, String password, String pluginName) {

        return ResponseEntity.ok("success");
    }
}
