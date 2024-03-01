package com.fayardev.plugindemo.service;

import com.fayardev.plugindemo.dto.PluginDto;
import com.fayardev.plugindemo.plugin.PluginTypeName;
import com.fayardev.plugindemo.plugin.adapter.UserPluginAdapter;
import com.fayardev.plugindemo.utils.CompressUtil;
import com.fayardev.plugindemo.utils.FileUtil;
import com.fayardev.plugindemo.utils.IDGenerator;
import com.fayardev.plugindemo.utils.UnzipUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class PluginTemplateService {

    @Value("${plugin.asset.directory.path}")
    private String pluginAssetDir;

    @Value("${template.path}")
    private String templatePath;

    @Value("${plugin.package}")
    private String pluginPackage;

    @Value("${plugin.template.path}")
    private String pluginTemplatePath;

    @Value("${plugin.source.path}")
    private String pluginSourcePath;

    private final PluginService service;

    @Autowired
    public PluginTemplateService(PluginService service) {
        this.service = service;
    }

    public File templateBuild(List<PluginTypeName> pluginTypeNames) throws InterruptedException {
        String pluginCode = "plugin" + IDGenerator.generate();
        String newZipFilePath = templatePath + File.separator + pluginCode + ".zip";

        UnzipUtil unzipUtil = UnzipUtil.getInstance();
        unzipUtil.setZipFilePath(pluginTemplatePath + ".zip");
        unzipUtil.setDestDirectory(templatePath);

        Thread unzipThread = new Thread(unzipUtil);
        unzipThread.start();
        unzipThread.join();

        pluginTypeNames.forEach(pluginTypeName -> {
            pluginAdapterAdd(pluginTypeName);
            pluginClassAdd(pluginCode, pluginTypeName);
        });

        CompressUtil compressUtil = CompressUtil.getInstance();
        compressUtil.setSourceDirPath(pluginTemplatePath);
        compressUtil.setZipFilePath(newZipFilePath);
        Thread compressThread = new Thread(compressUtil);
        compressThread.start();
        compressThread.join();

        FileUtil.deleteDirectory(new File(pluginTemplatePath));

        PluginDto pluginDto = PluginDto.builder().pluginName(pluginCode).pluginCode(pluginCode).build();
        service.add(pluginDto);

        return new File(newZipFilePath);
    }

    private void pluginClassAdd(String pluginCode, PluginTypeName pluginTypeName) {
        FileUtil.addDirectory(pluginTemplatePath + File.separator + pluginSourcePath + File.separator + pluginCode);

        String pluginClassPath = pluginTemplatePath + File.separator + pluginSourcePath + File.separator + pluginCode + File.separator + pluginTypeName  + ".java";
        String pluginClassPackage = "package " + pluginPackage + "." + pluginCode + ";";
        FileUtil.addFile(pluginClassPath);
        FileUtil.fileWriter(pluginClassPath, pluginAssetDir + File.separator + pluginTypeName + ".java");
        FileUtil.addFileContent(pluginClassPath, pluginClassPackage);
    }

    private void pluginAdapterAdd(PluginTypeName pluginTypeName) {
        String pluginAdapterPath = pluginTemplatePath + File.separator + pluginSourcePath + File.separator + "adapter" + File.separator;
        String pluginAdapterName = switch (pluginTypeName) {
            case USER_PLUGIN -> "UserPluginAdapter.java";
            case FILE_PLUGIN -> "FilePluginAdapter.java";
        };
        pluginAdapterPath += pluginAdapterName;

        FileUtil.addFile(pluginAdapterPath);
        FileUtil.fileWriter(pluginAdapterPath, pluginAssetDir + File.separator + pluginAdapterName);
    }
}
