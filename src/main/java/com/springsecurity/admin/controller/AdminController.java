package com.springsecurity.admin.controller;

import com.springsecurity.admin.DTO.*;
import com.springsecurity.entities.*;
import com.springsecurity.admin.services.AdminServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/place")
@RequiredArgsConstructor
public class AdminController {

    private final AdminServices adminServices;

    @GetMapping("/adminDashBoard")
    public String adminDashBoard(){
        return "adminDashBoard";
    }

    @GetMapping("/getAdminProfile")
    public ResponseEntity<PlaceResponse> getMyPlace(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(adminServices.getPlaceByUsername(username));
    }

    @PostMapping("/updatePlace")
    public ResponseEntity<PlaceResponse> updatePlace(Authentication authentication,@RequestBody PlaceUpdateReq placeUpdateReq){
        User user =(User)  authentication.getPrincipal();
        PlaceResponse placeResponse = adminServices.updatePlace(placeUpdateReq,user);
        return ResponseEntity.ok(placeResponse);
    }

}
