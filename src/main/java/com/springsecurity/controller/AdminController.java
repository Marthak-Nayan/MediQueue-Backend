package com.springsecurity.controller;

import com.springsecurity.DTO.CreateDoctorRequestDto;
import com.springsecurity.DTO.CreateDoctorRespDto;
import com.springsecurity.DTO.CreateRecipientReqDto;
import com.springsecurity.DTO.CreateRecipientResDto;
import com.springsecurity.services.AdminServices;
import com.springsecurity.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminServices adminServices;

    @GetMapping("/adminDashBoard")
    public String adminDashBoard(){

        return "adminDashBoard";
    }

    @GetMapping("/getAdminProfile")
    public String adminDashBoard(Authentication authentication) {
        String username = authentication.getName();
        String role = authentication.getAuthorities().toString();
        System.out.println("User: " + username + " | Role: " + role + " accessed Admin Dashboard");
        return "User: " + username + " | Role: " + role + " accessed Admin Dashboard";
    }


    @PostMapping("/create-doctor")
    public ResponseEntity<CreateDoctorRespDto> createDoctor(@RequestBody CreateDoctorRequestDto req) {
        return  ResponseEntity.ok(adminServices.createDoctor(req));
    }

    @PostMapping("/create-recipient")
    public ResponseEntity<CreateRecipientResDto> createRecipient(@RequestBody CreateRecipientReqDto req) {
        return  ResponseEntity.ok(adminServices.createRecipient(req));
    }
}
