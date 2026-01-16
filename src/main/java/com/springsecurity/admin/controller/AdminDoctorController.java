package com.springsecurity.admin.controller;

import com.springsecurity.admin.DTO.CreateDoctorRequestDto;
import com.springsecurity.admin.DTO.CreateDoctorRespDto;
import com.springsecurity.admin.DTO.DoctorRecordsResp;
import com.springsecurity.admin.services.AdminServices;
import com.springsecurity.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/doctor")
@AllArgsConstructor
public class AdminDoctorController {

    private final AdminServices adminServices;

    @PostMapping("/create-doctor")
    public ResponseEntity<CreateDoctorRespDto> createDoctor(@RequestBody CreateDoctorRequestDto req, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return  ResponseEntity.ok(adminServices.createDoctor(req,user));
    }

    @GetMapping("/getAllDoctor")
    public List<DoctorRecordsResp> getAllDoctors(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return adminServices.getAllDoctors(user);
    }

    @PutMapping("/getSpecificDoctor/{id}")
    public DoctorRecordsResp getSpecificDoctor(@PathVariable Long id,Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return adminServices.getSpecificDoctor(id,user);
    }

}
