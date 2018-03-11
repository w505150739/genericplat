package com.generic.genericcodegenerate.generate;

import com.generic.genericcodegenerate.utils.CodeResourceUtil;
import com.generic.genericcodegenerate.utils.CodeStringUtils;
import freemarker.template.Configuration;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.Locale;

public class BaseCodeFactory {

    protected String packageStyle;

    public BaseCodeFactory() {
    }

    public Configuration getConfiguration() throws IOException {
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(this.getClass(), CodeResourceUtil.FREEMARKER_CLASSPATH);
        cfg.setLocale(Locale.CHINA);
        cfg.setDefaultEncoding("UTF-8");
        return cfg;
    }

    public Configuration getConfigurationUserDefined() throws IOException {
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(this.getClass(), CodeResourceUtil.FREEMARKER_CLASSPATH_USERDEFINED);
        cfg.setLocale(Locale.CHINA);
        cfg.setDefaultEncoding("UTF-8");
        return cfg;
    }

    public String getCodePathServiceStyle(String path, String type, String entityPackage, String entityName) {
        StringBuilder str = new StringBuilder();
        if (!StringUtils.isNotBlank(type)) {
            throw new IllegalArgumentException("type is null");
        } else {
            String codeType = ((BaseCodeFactory.CodeType)Enum.valueOf(BaseCodeFactory.CodeType.class, type)).getValue();
            str.append(path);
            if (!"jsp".equals(type) && !"jspList".equals(type) && !"js".equals(type) && !"jsList".equals(type) && !"jsp_add".equals(type) && !"jsp_update".equals(type)) {
                str.append(CodeResourceUtil.CODEPATH);
            } else {
                str.append(CodeResourceUtil.JSPPATH);
            }

            str.append(StringUtils.lowerCase(entityPackage));
            str.append("/");
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
            String jsName;
            if (!"jsp".equals(type) && !"jspList".equals(type)) {
                if (!"jsp_add".equals(type) && !"jspList_add".equals(type)) {
                    if (!"jsp_update".equals(type) && !"jspList_update".equals(type)) {
                        if (!"js".equals(type) && !"jsList".equals(type)) {
                            str.append(StringUtils.capitalize(entityName));
                            str.append(codeType);
                            str.append(".java");
                        } else {
                            jsName = StringUtils.capitalize(entityName);
                            str.append(CodeStringUtils.getInitialSmall(jsName));
                            str.append(codeType);
                            str.append(".js");
                        }
                    } else {
                        jsName = StringUtils.capitalize(entityName);
                        str.append(CodeStringUtils.getInitialSmall(jsName));
                        str.append(codeType);
                        str.append("-update.jsp");
                    }
                } else {
                    jsName = StringUtils.capitalize(entityName);
                    str.append(CodeStringUtils.getInitialSmall(jsName));
                    str.append(codeType);
                    str.append("-add.jsp");
                }
            } else {
                jsName = StringUtils.capitalize(entityName);
                str.append(CodeStringUtils.getInitialSmall(jsName));
                str.append(codeType);
                str.append(".jsp");
            }

            return str.toString();
        }
    }

    public String getCodePathProjectStyle(String path, String type, String entityPackage, String entityName) {
        StringBuilder str = new StringBuilder();
        if (!StringUtils.isNotBlank(type)) {
            throw new IllegalArgumentException("type is null");
        } else {
            String codeType = ((BaseCodeFactory.CodeType)Enum.valueOf(BaseCodeFactory.CodeType.class, type)).getValue();
            str.append(path);
            if (!"jsp".equals(type) && !"jspList".equals(type) && !"js".equals(type) && !"jsList".equals(type) && !"jsp_add".equals(type) && !"jsp_update".equals(type)) {
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
            str.append(StringUtils.lowerCase(entityPackage));
            str.append("/");
            String jsName;
            if (!"jsp".equals(type) && !"jspList".equals(type)) {
                if (!"jsp_add".equals(type) && !"jspList_add".equals(type)) {
                    if (!"jsp_update".equals(type) && !"jspList_update".equals(type)) {
                        if (!"js".equals(type) && !"jsList".equals(type)) {
                            str.append(StringUtils.capitalize(entityName));
                            str.append(codeType);
                            str.append(".java");
                        } else {
                            jsName = StringUtils.capitalize(entityName);
                            str.append(CodeStringUtils.getInitialSmall(jsName));
                            str.append(codeType);
                            str.append(".js");
                        }
                    } else {
                        jsName = StringUtils.capitalize(entityName);
                        str.append(CodeStringUtils.getInitialSmall(jsName));
                        str.append(codeType);
                        str.append("-update.jsp");
                    }
                } else {
                    jsName = StringUtils.capitalize(entityName);
                    str.append(CodeStringUtils.getInitialSmall(jsName));
                    str.append(codeType);
                    str.append("-add.jsp");
                }
            } else {
                jsName = StringUtils.capitalize(entityName);
                str.append(CodeStringUtils.getInitialSmall(jsName));
                str.append(codeType);
                str.append(".jsp");
            }

            return str.toString();
        }
    }

    public String getPackageStyle() {
        return this.packageStyle;
    }

    public void setPackageStyle(String packageStyle) {
        this.packageStyle = packageStyle;
    }

    public static enum CodeType {
        serviceImpl("ServiceImpl"),
        dao("Dao"),
        service("ServiceI"),
        controller("Controller"),
        page("Page"),
        entity("Entity"),
        jsp(""),
        jsp_add(""),
        jsp_update(""),
        js(""),
        jsList("List"),
        jspList("List"),
        jspSubList("SubList");

        private String type;

        private CodeType(String type) {
            this.type = type;
        }

        public String getValue() {
            return this.type;
        }
    }
}
