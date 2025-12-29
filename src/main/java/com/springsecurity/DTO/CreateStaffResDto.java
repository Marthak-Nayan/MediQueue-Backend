package com.springsecurity.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateStaffResDto {
    // Login details
    private Long id;
    private String username;
    private String role;          // RECEPTIONIST, NURSE, LAB_TECHNICIAN

    // Staff basic details
    private String staffName;
    private Long mobileNo;
    private String email;

    // Job details
    private LocalDate joiningDate;
    private String employmentType; // FULL_TIME / PART_TIME / CONTRACT
    private Boolean active;

    private Long placeId;
}
