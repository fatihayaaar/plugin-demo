package com.fayardev.plugindemo.plugin;

import com.fayardev.plugindemo.loader.repository.ClassRepository;
import com.fayardev.plugindemo.plugin.adapter.FilePluginAdapter;
import com.fayardev.plugindemo.plugin.adapter.PluginAdapter;
import com.fayardev.plugindemo.plugin.adapter.UserPluginAdapter;

import java.lang.reflect.InvocationTargetException;

public class PluginContainer {

    private static PluginContainer instance;
    private ClassRepository classRepository;

    private PluginContainer() {
        classRepository = new ClassRepository();
    }

    public static PluginContainer getInstance() {
        if (instance == null)
            instance = new PluginContainer();
        return instance;
    }

    public PluginAdapter createPluginObject(PluginName pluginName) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Object object = classRepository.getClass(pluginName.toString()).getEnclosingConstructor().newInstance();
        return switch (pluginName) {
            case USER_PLUGIN -> (UserPluginAdapter) object;
            case FILE_PLUGIN -> (FilePluginAdapter) object;
        };
    }
}
