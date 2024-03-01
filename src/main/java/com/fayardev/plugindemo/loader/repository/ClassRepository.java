package com.fayardev.plugindemo.loader.repository;

import com.fayardev.plugindemo.loader.helper.ClassHelper;
import com.fayardev.plugindemo.loader.repository.abstracts.IClassRepository;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClassRepository implements IClassRepository<Class<?>> {

    private final ClassHelper classHelper;

    public ClassRepository() {
        classHelper = ClassHelper.getInstance();
    }

    @Override
    public void add(Class<?> clazz) {
        classHelper.add(clazz);
    }

    @Override
    public void addClassMap(Map<String, Class<?>> classMap) {
        classHelper.addAll(classMap);
    }

    @Override
    public Class<?> getClass(String className) {
        return classHelper.get(className);
    }
}
