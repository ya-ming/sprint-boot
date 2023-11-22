package io.javabrains.SpringBootSecurity.ConfigService;

import java.io.IOException;

// ConfigurationController.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ConfigurationController {

    private final ConfigurationRepository configurationRepository;
    private final JsonFileWriterService jsonFileWriterService;

    // @Autowired is not needed
    public ConfigurationController(ConfigurationRepository configurationRepository, JsonFileWriterService jsonFileWriterService) {
        this.configurationRepository = configurationRepository;
        this.jsonFileWriterService = jsonFileWriterService;
    }

    // @GetMapping("/config")
    // public String getConfig() {
    //     return "Property 1: " + configurationEntity.getConfiguredValue();
    // }

    @GetMapping("/configure")
    public String configurePage(Model model) {
        ConfigurationEntity configuration = configurationRepository.getConfigurationEntity();
        model.addAttribute("configuration", configuration);
        return "configure";
    }

    @PostMapping("/saveConfiguration")
    public String saveConfiguration(ConfigurationEntity configuration) {
        configurationRepository.setConfigurationEntity(configuration);

        try {
            // Update the JSON file with the new configuration
            jsonFileWriterService.updateJsonFile("config.json", configuration);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }

        return "redirect:/configure";
    }
}
