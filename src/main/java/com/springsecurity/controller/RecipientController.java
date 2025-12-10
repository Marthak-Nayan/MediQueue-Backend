package com.springsecurity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recipientDashBoard")
public class RecipientController {

    @GetMapping("/getRecipientProfile")
    public String recipient(Authentication authentication){
        String username = authentication.getName();
        String role = authentication.getAuthorities().toString();
        System.out.println("User: " + username + " | Role: " + role + " accessed Admin Dashboard");
        return "User: " + username + " | Role: " + role + " accessed Admin Dashboard";
    }

}
