package io.javabrains.SpringBootSecurity.ConfigService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JsonFileReaderService {

    private final ObjectMapper objectMapper;

    public JsonFileReaderService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T readJsonFile(String fileName, Class<T> valueType) throws IOException {
        ClassPathResource resource = new ClassPathResource(fileName);
        return objectMapper.readValue(resource.getInputStream(), valueType);
    }
}
