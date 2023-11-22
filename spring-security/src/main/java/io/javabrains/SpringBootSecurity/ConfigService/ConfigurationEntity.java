package io.javabrains.SpringBootSecurity.ConfigService;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(
  value = "classpath:config.json", 
  factory = JsonPropertySourceFactory.class)
@ConfigurationProperties
public class ConfigurationEntity {
    private int configuredValue;

    public int getConfiguredValue() {
        return configuredValue;
    }

    public void setConfiguredValue(int configuredValue) {
        this.configuredValue = configuredValue;
    }

    // Getters and setters
}

