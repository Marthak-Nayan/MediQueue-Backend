package com.springsecurity.auth.controller;

import com.springsecurity.auth.DTO.LoginRequestDto;
import com.springsecurity.auth.DTO.LoginResponseDto;
import com.springsecurity.auth.DTO.SignUpRequestDto;
import com.springsecurity.auth.DTO.SignUpResponseDto;
import com.springsecurity.entities.MasterDepartment;
import com.springsecurity.auth.services.AuthService;
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
