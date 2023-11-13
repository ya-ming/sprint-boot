package com.greglturnquist.learningspringboot.reactiveweb;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @Data is a Lombok annotation that generates getters, toString, hashCode, equals as well as setters for all non-final fields 
 * @NoArgsConstructor is a Lombok annotation to generate a no-argument constructor 
 * It has id and name fields for storing data 
 * We have crafted a custom constructor to load up fields of data 
 */
@Data
@NoArgsConstructor
public class Image {
    private String id;
    private String name;

    public Image(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
