package com.generate.genericgenerate;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.generate.genericgenerate.dao")
public class GenericGenerateApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenericGenerateApplication.class, args);
	}
}
