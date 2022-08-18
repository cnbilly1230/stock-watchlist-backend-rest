package com.bootcamp.java.watchlistexercise.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Configuration
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {
    private String origins;

    public String[] getOrigins() {
        return origins.split(",");
    }
}
