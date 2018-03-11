package com.generic.genericcodegenerate.generate;

import com.generic.genericcodegenerate.database.ReadTable;
import com.generic.genericcodegenerate.entity.Columnt;
import com.generic.genericcodegenerate.entity.GenerateMap;
import com.generic.genericcodegenerate.utils.CodeDateUtils;
import com.generic.genericcodegenerate.utils.CodeResourceUtil;
import com.generic.genericcodegenerate.utils.CodeStringUtils;
import com.generic.genericcodegenerate.utils.NonceUtils;
import com.generic.genericcodegenerate.utils.def.FtlDef;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CodeFactory extends BaseCodeFactory {

    private ReadTable dbFiledUtil = new ReadTable();
    private List<Columnt> columns = new ArrayList();
    private List<Columnt> originalColumns = new ArrayList();

    public CodeFactory() {
    }

    public void generateFile(String templateFileName, String type, Map data) {
        try {
            String entityPackage = data.get("entityPackage").toString();
            String entityName = data.get("entityName").toString();
            String fileNamePath = this.getCodePath(type, entityPackage, entityName);
            String fileDir = StringUtils.substringBeforeLast(fileNamePath, "/");
            Template template = this.getConfiguration().getTemplate(templateFileName);
            FileUtils.forceMkdir(new File(fileDir + "/"));
            Writer out = new OutputStreamWriter(new FileOutputStream(fileNamePath), CodeResourceUtil.SYSTEM_ENCODING);
            template.process(data, out);
            out.close();
        } catch (TemplateException var10) {
            var10.printStackTrace();
        } catch (IOException var11) {
            var11.printStackTrace();
        }

    }

    public static String getProjectPath() {
        String path = System.getProperty("user.dir").replace("\\", "/") + "/";
        return path;
    }

    public String getClassPath() {
        String path = Thread.currentThread().getContextClassLoader().getResource("./").getPath();
        return path;
    }

    public static void main(String[] args) {
        System.out.println(getProjectPath());
    }

    public String getTemplatePath() {
        String path = this.getClassPath() + CodeResourceUtil.TEMPLATEPATH;
        return path;
    }

    public String getCodePath(String type, String entityPackage, String entityName) {
        String path = getProjectPath();
        StringBuilder str = new StringBuilder();
        if (!StringUtils.isNotBlank(type)) {
            throw new IllegalArgumentException("type is nullN");
        } else {
            String codeType = ((CodeFactory.CodeType)Enum.valueOf(CodeFactory.CodeType.class, type)).getValue();
            str.append(path);
            if (!"jsp".equals(type) && !"jspList".equals(type)) {
                str.append(CodeResourceUtil.CODEPATH);
            } else {
                str.append(CodeResourceUtil.JSPPATH);
            }

            if ("Action".equalsIgnoreCase(codeType)) {
                str.append(StringUtils.lowerCase("action"));
            } else if ("ServiceImpl".equalsIgnoreCase(codeType)) {
                str.append(StringUtils.lowerCase("service/impl"));
            } else if ("ServiceI".equalsIgnoreCase(codeType)) {
                str.append(StringUtils.lowerCase("service"));
            } else if (!"List".equalsIgnoreCase(codeType)) {
                str.append(StringUtils.lowerCase(codeType));
            }

            str.append("/");
//            str.append(StringUtils.lowerCase(entityPackage));
//            str.append("/");
            if (!"jsp".equals(type) && !"jspList".equals(type)) {
                str.append(StringUtils.capitalize(entityName));
                str.append(codeType);
                str.append(".java");
            } else {
                String jspName = StringUtils.capitalize(entityName);
                str.append(CodeStringUtils.getInitialSmall(jspName));
                str.append(codeType);
                str.append(".jsp");
            }

            return str.toString();
        }
    }

    public Map<String, Object> execute(GenerateMap generateMap) {
        Map<String, Object> data = new HashMap();
        data.put("bussiPackage", CodeResourceUtil.bussiPackage);
        data.put("entityPackage", generateMap.getEntityPackage());
        data.put("entityName", generateMap.getEntityName());
        data.put("tableName", generateMap.getTableName());
        data.put("ftl_description", generateMap.getFtlDescription());
        data.put(FtlDef.JEECG_TABLE_ID, CodeResourceUtil.JEECG_GENERATE_TABLE_ID);
        data.put(FtlDef.JEECG_PRIMARY_KEY_POLICY, generateMap.getPrimaryKeyPolicy());
        data.put(FtlDef.JEECG_SEQUENCE_CODE, generateMap.getSequenceCode());
        data.put("ftl_create_time", CodeDateUtils.dateToString(new Date()));
        data.put("foreignKeys", generateMap.getForeignKeys());
        data.put(FtlDef.FIELD_REQUIRED_NAME, StringUtils.isNotEmpty(CodeResourceUtil.JEECG_UI_FIELD_REQUIRED_NUM) ? Integer.parseInt(CodeResourceUtil.JEECG_UI_FIELD_REQUIRED_NUM) : -1);
        data.put(FtlDef.SEARCH_FIELD_NUM, StringUtils.isNotEmpty(CodeResourceUtil.JEECG_UI_FIELD_SEARCH_NUM) ? Integer.parseInt(CodeResourceUtil.JEECG_UI_FIELD_SEARCH_NUM) : -1);
        data.put(FtlDef.FIELD_ROW_NAME, 1);

        try {
            this.columns = this.dbFiledUtil.readTableColumn(generateMap.getTableName());
            data.put("columns", this.columns);
            this.originalColumns = this.dbFiledUtil.readOriginalTableColumn(generateMap.getTableName());
            data.put("originalColumns", this.originalColumns);
            Iterator var3 = this.originalColumns.iterator();

            while(var3.hasNext()) {
                Columnt c = (Columnt)var3.next();
                if (c.getFieldName().toLowerCase().equals(CodeResourceUtil.JEECG_GENERATE_TABLE_ID.toLowerCase())) {
                    data.put("primary_key_type", c.getFieldType());
                }
            }
        } catch (Exception var4) {
            var4.printStackTrace();
            System.exit(-1);
        }

        long serialVersionUID = NonceUtils.randomLong() + NonceUtils.currentMills();
        data.put("serialVersionUID", String.valueOf(serialVersionUID));
        return data;
    }

    public void invoke(String templateFileName, String type,GenerateMap generateMap) {
        new HashMap();
        Map<String, Object> data = this.execute(generateMap);
        this.generateFile(templateFileName, type, data);
    }


    public static enum CodeType {
        serviceImpl("ServiceImpl"),
        service("ServiceI"),
        controller("Controller"),
        entity("Entity"),
        jsp(""),
        jspList("List");

        private String type;

        private CodeType(String type) {
            this.type = type;
        }

        public String getValue() {
            return this.type;
        }
    }

}
