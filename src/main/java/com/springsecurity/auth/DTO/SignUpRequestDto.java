package com.springsecurity.auth.DTO;

import com.springsecurity.DTO.DepartmentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<DepartmentDto> departmentIds;
}
