package com.springsecurity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {
    private String place_name;
    private String username;
    private String password;
    private String role;
}
