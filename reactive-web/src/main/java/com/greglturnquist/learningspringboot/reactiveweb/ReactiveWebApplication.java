package com.greglturnquist.learningspringboot.reactiveweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.reactive.HiddenHttpMethodFilter;

@SpringBootApplication
public class ReactiveWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveWebApplication.class, args);
	}

	/*
	 * DELETE is not a valid action for an HTML5 FORM, so Thymeleaf creates a hidden input field containing our desired verb while the enclosing form uses an HTML5 POST. 
	 * This gets transformed by Spring during the web call, resulting in the @DeleteMapping method being properly invoked with no effort on our end.
	 */
	@Bean
	HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter();
	}
}
