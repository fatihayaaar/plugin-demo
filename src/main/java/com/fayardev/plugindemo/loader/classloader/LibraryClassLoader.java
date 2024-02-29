package com.fayardev.plugindemo.loader.classloader;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class LibraryClassLoader extends URLClassLoader {
    public LibraryClassLoader(URL[] urls) {
        super(urls);
    }

    public void loadJar(String jarPath) throws Exception {
        File jarFile = new File(jarPath);
        URL jarUrl = jarFile.toURI().toURL();
        addURL(jarUrl);
    }

    public void loadAllClasses(String jarPath) throws Exception {
        File jarFile = new File(jarPath);
        URL jarUrl = jarFile.toURI().toURL();

        JarClassLoader jarLoader = new JarClassLoader(jarUrl);
        jarLoader.loadAllClasses();
    }
}


