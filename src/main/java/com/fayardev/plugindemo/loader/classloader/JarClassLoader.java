package com.fayardev.plugindemo.loader.classloader;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;

class JarClassLoader extends URLClassLoader {

    private static final Logger logger = Logger.getLogger(JarClassLoader.class.getName());

    public JarClassLoader(URL url) {
        super(new URL[] { url });
    }

    public Map<String, Class<?>> loadAllClasses() throws Exception {
        Map<String, Class<?>> classes = new HashMap<>();

        File jarFile = new File(getURLs()[0].toURI());
        try (JarFile jar = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jar.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().endsWith(".class")) {
                    String className = entry.getName().replaceAll("/", ".").replace(".class", "");
                    Class<?> loadedClass = loadClass(className);
                    classes.put(loadedClass.getName(), loadedClass);

                    logger.info("Loaded class: " + loadedClass.getName());
                }
            }
            return classes;
        }
    }
}
