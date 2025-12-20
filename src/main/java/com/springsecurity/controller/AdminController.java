package com.springsecurity.controller;

import com.springsecurity.DTO.*;
import com.springsecurity.entities.Doctor;
import com.springsecurity.entities.PlaceName;
import com.springsecurity.entities.Recipient;
import com.springsecurity.entities.User;
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

    @PostMapping("/create-department")
    public ResponseEntity<CreateDepartmentResp> createDepartment(@RequestBody CreateDepartmentReq req, Authentication authentication ) {
        User user =(User) authentication.getPrincipal();
        return  ResponseEntity.ok(adminServices.createDepartment(req,user));
    }

    @PostMapping("/create-doctor")
    public ResponseEntity<CreateDoctorRespDto> createDoctor(@RequestBody CreateDoctorRequestDto req) {
        return  ResponseEntity.ok(adminServices.createDoctor(req));
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
