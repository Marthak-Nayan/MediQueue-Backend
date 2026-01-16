package com.springsecurity.admin.controller;

import com.springsecurity.admin.DTO.CreateDepartmentReq;
import com.springsecurity.admin.DTO.CreateDepartmentResp;
import com.springsecurity.admin.DTO.UpdateDepartmentReq;
import com.springsecurity.admin.services.AdminServices;
import com.springsecurity.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/departments")
@AllArgsConstructor
public class AdminDepartmentController {

    private final AdminServices adminServices;

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
    public CreateDepartmentResp updateDepartment(@PathVariable Long id, @RequestBody UpdateDepartmentReq req, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return adminServices.updateDepartment(id,req,user);
    }

    @DeleteMapping("/deleteDepartment/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long id,Authentication authentication){
        User user = (User) authentication.getPrincipal();
        adminServices.deleteDepartment(id,user);
        return ResponseEntity.ok("Delete Department Successfully");
    }

}
