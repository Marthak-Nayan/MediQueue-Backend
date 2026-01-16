package com.springsecurity.admin.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateStafftReqDto {

    // Login details
    private String username;
    private String password;
    private String role;          // RECEPTIONIST, NURSE, LAB_TECHNICIAN

    // Staff basic details
    private String staffName;
    private Long mobileNo;
    private String email;
    private String gender;
    private LocalDate dob;
    private String address;

    // Job details
    private Integer experienceYears;
    private LocalDate joiningDate;
    private String employmentType; // FULL_TIME / PART_TIME / CONTRACT
    private Boolean active;

}
