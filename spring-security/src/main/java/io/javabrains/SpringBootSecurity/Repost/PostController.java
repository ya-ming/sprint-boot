package io.javabrains.SpringBootSecurity.Repost;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {
    @PostMapping("/post-rest")
    public ResponseEntity<String> processPostRequest(@RequestBody String requestBody) {
        return new ResponseEntity<>(requestBody, HttpStatus.OK);
    }
}
