package com.springsecurity.controller;

import com.springsecurity.DTO.*;
import com.springsecurity.entities.*;
import com.springsecurity.services.AdminServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminServices adminServices;


    //Place Controllers
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


    //Department Controllers
    @PostMapping("/create-department")
    public ResponseEntity<CreateDepartmentResp> createDepartment(@RequestBody CreateDepartmentReq req, Authentication authentication ) {
        User user =(User) authentication.getPrincipal();
        return  ResponseEntity.ok(adminServices.createDepartment(req,user));
    }
    @GetMapping("/getAllDepartment")
    public List<CreateDepartmentResp> getAllDepartment(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return adminServices.getAllDepartment(user);
    }
    @PutMapping("/updateDepartment/{id}")
    public CreateDepartmentResp updateDepartment(@PathVariable Long id,@RequestBody UpdateDepartmentReq req,Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return adminServices.updateDepartment(id,req,user);
    }
    @DeleteMapping("/deleteDepartment/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long id,Authentication authentication){
        User user = (User) authentication.getPrincipal();
        adminServices.deleteDepartment(id,user);
        return ResponseEntity.ok("Delete Department Successfully");
    }

    //Doctor Controllers
    @PostMapping("/create-doctor")
    public ResponseEntity<CreateDoctorRespDto> createDoctor(@RequestBody CreateDoctorRequestDto req,Authentication authentication) {
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


    //Staff Controllers
    @PostMapping("/create-staff")
    public ResponseEntity<CreateStaffResDto> createRecipient(@RequestBody CreateStafftReqDto req, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return  ResponseEntity.ok(adminServices.createStaff(req,user));
    }
//    @GetMapping("/getAllStaff")
//    public List<CreateStaffResDto> getAllStaff(Authentication authentication){
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
