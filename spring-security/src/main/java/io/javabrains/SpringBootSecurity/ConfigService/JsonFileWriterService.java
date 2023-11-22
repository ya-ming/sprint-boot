package io.javabrains.SpringBootSecurity.ConfigService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class JsonFileWriterService {

    private final ObjectMapper objectMapper;

    // @Autowired is not needed
    public JsonFileWriterService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void updateJsonFile(String fileName, Object updatedConfig) throws IOException {
        ClassPathResource resource = new ClassPathResource(fileName);
        Path filePath = resource.getFile().toPath();

        // Serialize the updated configuration to JSON
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        String updatedConfigJson = objectWriter.writeValueAsString(updatedConfig);

        // Write the updated JSON content back to the file
        Files.write(filePath, updatedConfigJson.getBytes());

        // If you need to update the file atomically, you can use Files.move
        // Files.move(updatedFilePath, filePath, StandardCopyOption.REPLACE_EXISTING);
    }
}
