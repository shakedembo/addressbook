package com.example.demo.addressbook;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloController {

    @RequestMapping
    public String Hello() {
        return String.format("Hello from '%s'", this.getClass().getName());
    }
}
