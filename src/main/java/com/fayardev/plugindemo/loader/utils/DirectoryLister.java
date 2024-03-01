package com.fayardev.plugindemo.loader.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DirectoryLister {
    public static List<String> list(String path) {
        List<String> dirNames = new ArrayList<>();
        File dir = new File(path);

        if (dir.exists() && dir.isDirectory()) {
            String[] dirs = dir.list();

            assert dirs != null;
            Collections.addAll(dirNames, dirs);
        }
        return dirNames;
    }
}
