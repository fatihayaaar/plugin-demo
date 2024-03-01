package com.fayardev.plugindemo.loader.helper;

import java.util.HashMap;
import java.util.Map;

public class ClassHelper {

    private static ClassHelper instance;
    public Map<String, Class<?>> classes;

    private ClassHelper() {
        init();
    }

    public static ClassHelper getInstance() {
        if (instance == null)
            instance = new ClassHelper();
        return instance;
    }

    private void init() {
        classes = new HashMap<>();
    }

    public void add(Class<?> clazz) {
        classes.put(clazz.getName(), clazz);
    }

    public void addAll(Map<String, Class<?>> classMap) {
        classes.putAll(classMap);
    }

    public Class<?> get(String className) {
        return classes.get(className);
    }
}
