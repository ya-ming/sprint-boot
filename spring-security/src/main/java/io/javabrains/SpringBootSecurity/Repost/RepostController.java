package io.javabrains.SpringBootSecurity.Repost;

import io.javabrains.SpringBootSecurity.ConfigService.ConfigurationEntity;
import io.javabrains.SpringBootSecurity.ConfigService.ConfigurationRepository;
import io.javabrains.SpringBootSecurity.ConfigService.JsonFileWriterService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Controller
public class RepostController {
    @GetMapping("/repost")
    public String repostPage(Model model) {
        // Add the model attribute for the form
        model.addAttribute("postValue", new PostValue());
        return "repost";
    }

    @PostMapping("/repost")
    public String repost(@RequestBody String requestBody, Model model) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Set up the request entity with headers and body
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Define the target URL
        String targetUrl = "https://127.0.0.1:8443/post-rest";

        // Make the POST request
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(targetUrl, requestEntity, String.class);

        model.addAttribute("result", responseEntity.getStatusCode());
        model.addAttribute("postValue", new PostValue());
        return "repost";
    }
}
