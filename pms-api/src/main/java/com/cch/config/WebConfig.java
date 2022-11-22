package com.cch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        addMapping配置过滤所有请求
        registry.addMapping("/**")
//                任何来源
                .allowedOrigins("*")
//                允许的方法
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
//                允许所有的请求头
                .allowedHeaders("*")
//                允许的证书
                .allowCredentials(true);
    }
}
