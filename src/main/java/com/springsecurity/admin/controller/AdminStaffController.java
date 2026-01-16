package com.springsecurity.admin.controller;

import com.springsecurity.admin.DTO.CreateStaffResDto;
import com.springsecurity.admin.DTO.CreateStafftReqDto;
import com.springsecurity.admin.services.AdminServices;
import com.springsecurity.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/staff")
@AllArgsConstructor
public class AdminStaffController {

    private final AdminServices adminServices;

    @PostMapping("/create-staff")
    public ResponseEntity<CreateStaffResDto> createRecipient(@RequestBody CreateStafftReqDto req, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return  ResponseEntity.ok(adminServices.createStaff(req,user));
    }

//    @GetMapping("/getAllStaff")
//    public List<CreateStaffResDto> getAllStaff(Authentication authentication){
//        User user = (User) authentication.getPrincipal();
//        User user = (User) authentication.getPrincipal();
//        return adminServices.getAllStaff(user);
//    }

//    @PutMapping("/getAllSpecificStaffRole/{staffRole}")
//    public List<CreateStaffResDto> getAllSpecificStaffRole(@PathVariable String staffRole, Authentication authentication){
//        User user = (User) authentication.getPrincipal();
//        return adminServices.getAllSpecificStaffRole(user,staffRole);
//    }

//    @PutMapping("/getSpecificStaff/{id}")
//    public CreateStaffResDto getSpecificStaff(@PathVariable Long id, Authentication authentication){
//        User user = (User) authentication.getPrincipal();
//        return adminServices.getSpecificStaff(user,id);
//    }

}
