package com.example.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component("barFormatter")
public class BarFormatter implements Formatter {
    public String format() {
        return "bar";
    }
}
