package com.springsecurity.controller;

import com.springsecurity.DTO.*;
import com.springsecurity.entities.*;
import com.springsecurity.services.AdminServices;
import com.springsecurity.services.AuthService;
import com.springsecurity.services.DoctorServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminServices adminServices;


    //Place Tasks
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


    //Department Tasks
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

    //
    @PostMapping("/create-doctor")
    public ResponseEntity<CreateDoctorRespDto> createDoctor(@RequestBody CreateDoctorRequestDto req,Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return  ResponseEntity.ok(adminServices.createDoctor(req,user));
    }


    @PostMapping("/create-recipient")
    public ResponseEntity<CreateRecipientResDto> createRecipient(@RequestBody CreateRecipientReqDto req) {
        return  ResponseEntity.ok(adminServices.createRecipient(req));
    }


    @GetMapping("/getAllDoctors")
    public List<Doctor> getAllDoctors(){
        return adminServices.getAllDoctors();
    }


    @GetMapping("/getAllRecipient")
    public List<Recipient> getAllRecipient(){
        return adminServices.getAllRecipient();
    }


}
