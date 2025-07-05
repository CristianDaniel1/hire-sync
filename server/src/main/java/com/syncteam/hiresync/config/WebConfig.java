package com.syncteam.hiresync.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/resumes/**")
                .addResourceLocations("file:uploads/resumes/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/files/resumes/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET");
    }
}