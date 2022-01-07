package com.itheima.reggie.demo;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@Slf4j
@ServletComponentScan("com.itheima.reggie.demo.filter")
@SpringBootApplication
public class ReggieApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReggieApplication.class, args);
		log.info("项目启动成功！");
	}

}
