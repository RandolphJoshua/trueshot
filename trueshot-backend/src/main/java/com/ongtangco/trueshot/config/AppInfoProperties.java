package com.ongtangco.trueshot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.info")
public class AppInfoProperties {
    private String storeName;
    private String supportEmail;
    private String supportNumber;
    private String heroMessage;
}
