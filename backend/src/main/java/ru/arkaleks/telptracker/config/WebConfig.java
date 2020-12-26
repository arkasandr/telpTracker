package ru.arkaleks.telptracker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
                .allowedMethods("Access-Control-Allow-Origin: *")
                .allowedHeaders("Access-Control-Allow-Headers: Origin, Content-Type, X-Auth-Token")
                .allowedHeaders("Access-Control-Allow-Methods: GET, POST, PATCH, PUT, DELETE, OPTIONS")
                .allowedHeaders("Access-Control-Allow-Headers: Origin, Content-Type, X-Auth-Token");
    }
}