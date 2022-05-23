package com.bornya.pim.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {
    /**
     * 配置资源访问
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将url为 /static/** 的请求映射到 /static/ 路径下进行查找
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}

