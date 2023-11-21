package io.javabrains.SpringBootSecurity.ConfigService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ConfigurationRepository {
    ConfigurationEntity configurationEntity;

    @Autowired
    public ConfigurationRepository(ConfigurationEntity configurationEntity) {
        this.configurationEntity = configurationEntity;
    }

    // public ConfigurationRepository() {
    //     configurationEntity = new ConfigurationEntity();
    //     configurationEntity.setConfiguredValue(5);
    // }

    public ConfigurationEntity getConfigurationEntity() {
        return configurationEntity;
    }

    public void setConfigurationEntity(ConfigurationEntity configurationEntity) {
        this.configurationEntity = configurationEntity;
    }
}
