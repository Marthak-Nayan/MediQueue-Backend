package com.springsecurity.controller;

import com.springsecurity.DTO.CreateDoctorRequestDto;
import com.springsecurity.DTO.CreateDoctorRespDto;
import com.springsecurity.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/adminDashBoard")
@RequiredArgsConstructor
public class AdminController {

    private final AuthService authService;

    @GetMapping("/adminDashBoard")
    public String adminDashBoard(Authentication authentication) {
        String username = authentication.getName();
        String role = authentication.getAuthorities().toString();
        System.out.println("User: " + username + " | Role: " + role + " accessed Admin Dashboard");
        return "Welcome Admin";
    }


//    @PostMapping("/create-doctor")
//    public String doctor(){
//        return "DOCTOR";
//    }
//    public ResponseEntity<CreateDoctorRespDto> createDoctor(@RequestBody CreateDoctorRequestDto req) {
//        return  ResponseEntity.ok(authService.createDoctor(req));
//    }
}
