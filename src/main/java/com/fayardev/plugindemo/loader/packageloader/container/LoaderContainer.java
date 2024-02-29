package com.fayardev.plugindemo.loader.packageloader.container;

import com.fayardev.plugindemo.loader.packageloader.LibraryLoader;
import com.fayardev.plugindemo.loader.packageloader.PluginLoader;
import com.fayardev.plugindemo.loader.packageloader.abstracts.ILoader;

public class LoaderContainer {

    private static LoaderContainer loaderContainer;

    private LoaderContainer() {
    }

    public static LoaderContainer getInstance() {
        if (loaderContainer == null)
            loaderContainer = new LoaderContainer();
        return loaderContainer;
    }

    public ILoader getClassLoader(LoaderName loaderName) {
        return switch (loaderName) {
            case PLUGIN_LOADER -> new PluginLoader();
            case LIBRARY_LOADER -> new LibraryLoader();
        };
    }
}
