package com.generic.genericcodegenerate.generate;

import com.generic.genericcodegenerate.entity.GenerateMap;
import org.apache.log4j.Logger;

public class CodeGenerate{

    private static final Logger log = Logger.getLogger(CodeGenerate.class);

//    static {
//        createFileProperty.setActionFlag(true);
//        createFileProperty.setServiceIFlag(true);
//        createFileProperty.setJspFlag(true);
//        createFileProperty.setServiceImplFlag(true);
//        createFileProperty.setJspMode("01");
//        createFileProperty.setPageFlag(true);
//        createFileProperty.setEntityFlag(true);
//    }

    public CodeGenerate() {
    }

    public void generateToFile(GenerateMap generateMap) {
        log.info("----jeecg---Code----Generation----[单表模型:" + generateMap.getTableName() + "]------- 生成中。。。");
        CodeFactory codeFactory = new CodeFactory();

        if (generateMap.getCreateFileProperty().isServiceImplFlag()) {
            codeFactory.invoke("serviceImplTemplate.ftl", "serviceImpl",generateMap);
        }

        if (generateMap.getCreateFileProperty().isServiceIFlag()) {
            codeFactory.invoke("serviceITemplate.ftl", "service",generateMap);
        }

        if (generateMap.getCreateFileProperty().isActionFlag()) {
            codeFactory.invoke("controllerTemplate.ftl", "controller",generateMap);
        }

        if (generateMap.getCreateFileProperty().isEntityFlag()) {
            codeFactory.invoke("entityTemplate.ftl", "entity",generateMap);
        }

        log.info("----jeecg----Code----Generation-----[单表模型：" + generateMap.getTableName() + "]------ 生成完成。。。");
    }
}
