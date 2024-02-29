package com.fayardev.plugindemo.plugin;

public enum PluginName {

    USER_PLUGIN("User"),
    FILE_PLUGIN("File");

    private final String value;

    PluginName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
