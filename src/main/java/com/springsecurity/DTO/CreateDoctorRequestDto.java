package com.springsecurity.DTO;

import com.springsecurity.entities.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDoctorRequestDto {
    private String username;
    private String password;

    private String doctorName;
    private Long mobileNo;
    private String email;
    private String gender;
    private LocalDate dob;
    private String address;

    private String registrationNo;
    private String qualification;
    private Integer experienceYears;
    private String designation;

    private LocalDate joiningDate;
    private String employmentType;
    private Boolean active;
    private String role;

    private List<Long> departmentIds;

}
