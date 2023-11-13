package com.greglturnquist.learningspringboot.reactiveweb;

import java.io.IOException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/*
 * @Controller: This indicates that it is a web controller, and will be registered by Spring Boot to handle web requests. 
 * BASE_PATH: This is a static string used to define the base of many routes. 
 * FILENAME: This is a pattern for filenames where the "." is included. Otherwise, 
 *      Spring WebFlux will use the suffix as part of content negotiation (for example, .json would try to fetch a JSON response, while .xml would try to fetch an XML response). 
 * ImageService: This is injected via constructor injection so that we can tap our reactive image handling code we just wrote. 
 */

@Controller
public class HomeController {
    private static final String BASE_PATH = "/images";
    private static final String FILENAME = "{filename:.+}";

    private final ImageService imageService;

    public HomeController(ImageService imageService) {
        this.imageService = imageService;
    }

    /*
     * @GetMapping defines a route mapping for GET BASE_PATH + "/" + FILENAME + "/raw". It also sets the Content-Type header to properly render it as an image. 
     * @ResponseBody indicates that this method's response will be written directly into the HTTP response body. 
     * @PathVariable flags that the input filename will be extracted from the route's {filename} attribute. 
     * Mono<ResponseEntity<?>> shows that we are returning a single response, reactively. 
     * ResponseEntity<?> describes a generic HTTP response. The code taps our image service's findOneImage() using filename. 
     * It's possible to have incoming arguments wrapped in Reactor types such as Mono<String>. 
     *      Since this argument comes from the route and not the request body, there is nothing gained in this situation. 
     * Since findOneImage returns a Mono<Resource>, we map over it, transforming this Spring Resource into a ResponseEntity 
     *      including a Content-Length response header as well as the data embedded in the body. 
     * In the event of an exception, it will return an HTTP Bad Response. 
     */
    
    @GetMapping(value = BASE_PATH + "/" + FILENAME + "/raw", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public Mono<ResponseEntity<?>> oneRawImage(
            @PathVariable String filename) {
        return imageService.findOneImage(filename)
                // Since findOneImage returns a Mono<Resource>, 
                // we map over it, transforming this Spring Resource into a ResponseEntity
                // including a Content-Length response header as well as the data embedded in the body
                .map(resource -> {
                    try {
                        return ResponseEntity.ok()
                                .contentLength(resource.contentLength())
                                .body(new InputStreamResource(
                                        resource.getInputStream()));
                    } catch (IOException e) {
                        return ResponseEntity.badRequest()
                                .body("Couldn't find " + filename + " => " + e.getMessage());
                    }
                });
    }

    /*
     * A collection of incoming FilePart objects is represented as a Flux 
     * The flux of files is handed directly to the image service to be processed 
     * .then() indicates that once the method is complete, it will then return a redirect:/ directive (wrapped in a Mono), issuing an HTML redirect to / 
     */
    @PostMapping(value = BASE_PATH)
    public Mono<String> createFile(@RequestPart(name = "file")
        Flux<FilePart> files) {
            return imageService.createImage(files)
            .then(Mono.just("redirect:/"));
        }

    /*
     * Using Spring's @DeleteMapping annotation, this method is ready for HTTP DELETE operations
     * It's keyed to the same BASE_PATH + "/" + FILENAME pattern 
     * It taps the image service's deleteImage() method 
     * It uses then() to wait until the delete is done before returning back a mono-wrapped redirect:/ directive 
     */
    @DeleteMapping(BASE_PATH + "/" + FILENAME)
    public Mono<String> deleteFile(@PathVariable String filename) {
        return imageService.deleateImage(filename)
        .then(Mono.just("redirect:/"));
    }

    /*
     * @GetMapping is used to explicitly map the "/" route. 
     * It accepts a Model object, giving us a place to load data reactively. 
     * addAttribute() lets us assign the image service's findAllImages() Flux to the template model's images attribute. 
     * The method returns "index" wrapped in a Mono, ensuring the whole thing is chained together, top to bottom, 
     *      to kick off when Spring WebFlux subscribes to render the template. 
     */
    @GetMapping("/")
    public Mono<String> index(Model model) {
        model.addAttribute("images", imageService.findAllImages());
        return Mono.just("index");
    }
}
