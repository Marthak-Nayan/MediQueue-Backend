package com.springsecurity.doctor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctor")
public class DoctorController {

    @GetMapping("/doctorDashBoard")
    public String doctorDashBoard(){
        return "doctorDashBoard";
    }

    @GetMapping("/getDoctorProfile")
    public String doctor(Authentication authentication){
        String username = authentication.getName();
        String role = authentication.getAuthorities().toString();
        System.out.println("User: " + username + " | Role: " + role + " accessed Admin Dashboard");
        return "User: " + username + " | Role: " + role + " accessed Admin Dashboard";
    }

}
