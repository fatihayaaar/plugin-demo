package com.fayardev.plugindemo.loader.classloader;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

class JarClassLoader extends URLClassLoader {
    public JarClassLoader(URL url) {
        super(new URL[] { url });
    }

    public void loadAllClasses() throws Exception {
        File jarFile = new File(getURLs()[0].toURI());
        try (JarFile jar = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jar.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().endsWith(".class")) {
                    String className = entry.getName().replaceAll("/", ".").replace(".class", "");
                    Class<?> loadedClass = loadClass(className);
                    System.out.println("Loaded class: " + loadedClass.getName());
                }
            }
        }
    }
}
