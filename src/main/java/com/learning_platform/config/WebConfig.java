package com.learning_platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Allow all endpoints
//                        .allowedOrigins("http://localhost:5173", "http://learning-platform-dev-bhcr.s3-website-us-east-1.amazonaws.com/") // Allow requests from frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS","PATCH") // Allow these methods
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
