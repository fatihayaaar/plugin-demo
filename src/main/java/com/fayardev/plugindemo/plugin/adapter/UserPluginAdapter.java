package com.fayardev.plugindemo.plugin.adapter;

public interface UserPluginAdapter extends PluginAdapter {

    boolean confirm(String username, String password);
}
