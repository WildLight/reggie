package com.itheima.reggie.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始静态资源映射。。。");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        log.info("{}-->{}","/backend/**","classpath:/backend");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/backend/");
        log.info("{}-->{}","/front/**","classpath:/backend");
    }
}