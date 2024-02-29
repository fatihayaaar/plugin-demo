package com.fayardev.plugindemo.plugin;

public enum PluginTypeName {

    USER_PLUGIN("User"),
    FILE_PLUGIN("File");

    private final String value;

    PluginTypeName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
