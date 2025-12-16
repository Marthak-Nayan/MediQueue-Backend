package com.springsecurity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {
    private String placename;
    private String username;
    private String password;
    private String email;
    private Long mobile;
    private String address;
    private String role;
}
