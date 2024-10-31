package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${web.cors.allowedOrigins}")
    private String allowedOrigins;

    @Value("${web.cors.allowedMethods}")
    private String allowedMethods;

    @Value("${web.cors.allowedHeaders}")
    private String allowedHeaders;

    @Value("${web.cors.exposedHeaders}")
    private String exposedHeaders;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins.split(","))
                .allowedMethods(allowedMethods.split(","))
                .allowedHeaders(allowedHeaders.split(","))
                .exposedHeaders(exposedHeaders.split(","))
                .allowCredentials(false);
    }
}
