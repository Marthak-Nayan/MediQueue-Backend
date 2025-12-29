package com.springsecurity.DTO;

import com.springsecurity.entities.Department;
import com.springsecurity.entities.MasterDepartment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

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
