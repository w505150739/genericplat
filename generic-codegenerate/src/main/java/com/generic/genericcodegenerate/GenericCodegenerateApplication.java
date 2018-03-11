package com.generic.genericcodegenerate;

import com.generic.genericcodegenerate.entity.CreateFileProperty;
import com.generic.genericcodegenerate.entity.GenerateMap;
import com.generic.genericcodegenerate.generate.CodeGenerate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GenericCodegenerateApplication {

	public static void main(String[] args) {
		//SpringApplication.run(GenericCodegenerateApplication.class, args);

		/**
		 * 表名
		 */
		String tableName = "t_sys_menu";

		/**
		 * 实体包名
		 */
		String entityPackage = "entity";

		/**
		 * 实体名称
		 */
		String entityName = "TSysMenu";

		/**
		 * 描述
		 */
		String ftlDescription = "系统菜单表";

		/**
		 * 主键策略
		 */
		String primaryKeyPolicy = "";

		String sequenceCode = "";

		CreateFileProperty property = new CreateFileProperty();
		property.setActionFlag(true);
		property.setEntityFlag(true);
		property.setServiceIFlag(true);
		property.setServiceImplFlag(true);

		GenerateMap generateMap = new GenerateMap();
		generateMap.setCreateFileProperty(property);
		generateMap.setEntityName(entityName);
		generateMap.setEntityPackage(entityPackage);
		generateMap.setFtlDescription(ftlDescription);
		generateMap.setPrimaryKeyPolicy(primaryKeyPolicy);
		generateMap.setSequenceCode(sequenceCode);
		generateMap.setTableName(tableName);
//public CodeGenerate(String entityPackage, String entityName, String tableName, String ftlDescription, CreateFileProperty createFileProperty, String primaryKeyPolicy, String sequenceCode)
//		System.out.println("----jeecg--------- Code------------- Generation -----[单表模型]------- 生成中。。。");
		(new CodeGenerate()).generateToFile(generateMap);
//		System.out.println("----jeecg--------- Code------------- Generation -----[单表模型]------- 生成完成。。。");
	}
}
