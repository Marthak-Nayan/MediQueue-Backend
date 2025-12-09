package com.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePage {

    @GetMapping("/home")
    public String sayHello(){
        return "Hello, Good Morning";
    }

    @GetMapping("/bye")
    public String sayBye(){
        return "Hello, Good Night";
    }

    @GetMapping("/doctor")
    public String doctor(){
        return "doctor";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }
}
