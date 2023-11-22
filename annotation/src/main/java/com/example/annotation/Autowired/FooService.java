package com.example.annotation.Autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class FooService {
    // Case 1: @Autowired on Properties, Spring injects fooFormatter when FooService is created
    // @Autowired
    private FooFormatter fooFormatter;

    // Case 2: @Autowired on Setters, the setter method is called with the instance of FooFormatter when FooService is created
    // @Autowired
    public void setFooFormatter(FooFormatter fooFormatter) {
        this.fooFormatter = fooFormatter;
    }

    // Case 3: @Autowired on Constructors, an instance of FooFormatter is injected by Spring as an argument to the FooService constructor
    // @Autowired
    public FooService(FooFormatter fooFormatter) {
        this.fooFormatter = fooFormatter;
    }

    @Autowired
    @Qualifier("fooFormatter")
    private Formatter formatter;
}
