package com.generic.genericcodegenerate.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GenerateMap {
    private String entityPackage;
    private String entityName;
    private String tableName;
    private String ftlDescription;
    private String primaryKeyPolicy;
    private String sequenceCode;
    private String[] foreignKeys;
    public int FIELD_ROW_NUM = 1;
    private CreateFileProperty createFileProperty = new CreateFileProperty();
}
