package com.fayardev.plugindemo.service;

import com.fayardev.plugindemo.utils.CompressUtil;
import com.fayardev.plugindemo.utils.FileUtil;
import com.fayardev.plugindemo.utils.IDGenerator;
import com.fayardev.plugindemo.utils.UnzipUtil;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class PluginTemplateService {

    public File templateBuild() throws InterruptedException {
        String pluginCode = "plugin" + IDGenerator.generate();
        String sourceDirPath = "template/plugin-template";
        String newZipFilePath = "template/" + pluginCode + ".zip";

        UnzipUtil unzipUtil = UnzipUtil.getInstance();
        unzipUtil.setZipFilePath(sourceDirPath + ".zip");
        unzipUtil.setDestDirectory("template");

        Thread unzipThread = new Thread(unzipUtil);
        unzipThread.start();
        unzipThread.join();

        FileUtil.addDirectory("template/plugin-template/src/main/java/com/fayardev/plugindemo/plugin/" + pluginCode);
        FileUtil.addFile("template/plugin-template/src/main/java/com/fayardev/plugindemo/plugin/" + pluginCode + "/User.java");
        FileUtil.fileWriter("template/plugin-template/src/main/java/com/fayardev/plugindemo/plugin/" + pluginCode + "/User.java", "assets/User.java");
        FileUtil.addFileContent("template/plugin-template/src/main/java/com/fayardev/plugindemo/plugin/" + pluginCode + "/User.java", "package com.fayardev.plugindemo." + pluginCode + ";");

        CompressUtil compressUtil = CompressUtil.getInstance();
        compressUtil.setSourceDirPath(sourceDirPath);
        compressUtil.setZipFilePath(newZipFilePath);
        Thread compressThread = new Thread(compressUtil);
        compressThread.start();
        compressThread.join();

        //FileUtil.deleteDirectory(new File(sourceDirPath));

        return new File(newZipFilePath);
    }
}
