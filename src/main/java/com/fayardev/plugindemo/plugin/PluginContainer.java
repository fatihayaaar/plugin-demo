package com.fayardev.plugindemo.plugin;

import com.fayardev.plugindemo.loader.repository.ClassRepository;
import com.fayardev.plugindemo.plugin.adapter.FilePluginAdapter;
import com.fayardev.plugindemo.plugin.adapter.PluginAdapter;
import com.fayardev.plugindemo.plugin.adapter.UserPluginAdapter;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;

public class PluginContainer {

    private static PluginContainer instance;
    @Getter private ClassRepository classRepository;
    private final String pluginPackage = "com.fayardev.plugindemo";

    private PluginContainer() {
        classRepository = new ClassRepository();
    }

    public static PluginContainer getInstance() {
        if (instance == null)
            instance = new PluginContainer();
        return instance;
    }

    public PluginAdapter createPluginObject(String pluginName, PluginTypeName pluginType) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Object object = classRepository.getClass(pluginPackage + "." + pluginName + "." +  pluginType.toString()).getDeclaredConstructor().newInstance();
        return switch (pluginType) {
            case USER_PLUGIN -> (UserPluginAdapter) object;
            case FILE_PLUGIN -> (FilePluginAdapter) object;
        };
    }

}
