package com.bootcamp.java.watchlistexercise.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private CorsProperties corsProperties;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(corsProperties.getOrigins())
                .allowedMethods("*")
                .allowCredentials(true)
                .allowedHeaders("*");
    }
}
