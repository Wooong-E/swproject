package com.example.swproject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // uploadPath에 해당하는 로컬 경로의 파일들을 /uploads/ URL 패턴으로 접근할 수 있도록 매핑
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath + "/");

        // For images in src/main/resources/static/images
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");

        // Re-add the default static resource handler for all other static content
        // This ensures CSS, JS, and other static files are served.
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/", "classpath:/public/", "classpath:/resources/", "classpath:/META-INF/resources/");
    }
}
