package com.springsecurity.controller;

import com.springsecurity.DTO.LoginRequestDto;
import com.springsecurity.DTO.LoginResponseDto;
import com.springsecurity.DTO.SignUpRequestDto;
import com.springsecurity.DTO.SignUpResponseDto;
import com.springsecurity.entities.MasterDepartment;
import com.springsecurity.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDTO){
        return  ResponseEntity.ok(authService.login(loginRequestDTO));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signup(@RequestBody SignUpRequestDto signupRequestDto){
        return  ResponseEntity.ok(authService.signup(signupRequestDto));
    }

    @GetMapping("/getAllDepartments")
    private List<MasterDepartment> getAllDepartments(){
        return authService.getAllDepartments();
    }

}
