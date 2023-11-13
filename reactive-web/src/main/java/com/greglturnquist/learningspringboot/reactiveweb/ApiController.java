package com.greglturnquist.learningspringboot.reactiveweb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ApiController {
    private static final Logger log = LoggerFactory.getLogger(ApiController.class);
    private static final String API_BASE_PATH = "/api";

    /*
     * Using the same Flux.just() helper, we return a rather contrived list 
     * The Spring controller returns a Flux<Image> 
     * Reactor type, leaving Spring in charge of properly subscribing to this flow when the time is right 
     */
    @GetMapping(API_BASE_PATH + "/images")
    Flux<Image> images() {
        return Flux.just(
            new Image("1", "learning-spring-boot-cover.jpg"),
            new Image("2", "learning-spring-boot-2nd-edition-cover.jpg"),
            new Image("3", "bazinga.png")
        );
        
    }

    /*
     * @PostMapping indicates this method will respond to HTTP POST calls. The route is listed in the annotation. 
     * @RequestBody instructs Spring to fetch data from the HTTP request body. 
     * The container for our incoming data is another Flux of Image objects. 
     * To consume the data, we map over it. In this case, we simply log it and pass the original Image onto the next step of our flow. 
     * To wrap this logging operation with a promise, we invoke Flux.then(), which gives us Mono<Void>. 
     *      Spring WebFlux will make good on this promise, subscribing to the results when the client makes a request. 
     */
    @PostMapping(API_BASE_PATH + "/images")
    Mono<Void> create(@RequestBody Flux<Image> images) {
        return images.map(image -> {
            log.info("We will save " + image + " to a Reactive database soon!");
            return image;
        })
        .then();
    }
}
