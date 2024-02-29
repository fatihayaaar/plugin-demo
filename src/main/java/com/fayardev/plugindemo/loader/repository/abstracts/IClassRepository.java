package com.fayardev.plugindemo.loader.repository.abstracts;

import java.util.Map;

public interface IClassRepository<T> {

    void add(T clazz);

    void addClassMap(Map<String, T> classMap);

    T getClass(String className);
}
