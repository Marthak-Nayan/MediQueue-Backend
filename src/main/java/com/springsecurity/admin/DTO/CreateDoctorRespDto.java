package com.springsecurity.admin.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateDoctorRespDto {

    private Long id;
    private String username;

    private String doctorName;
    private Long mobileNo;
    private String email;
    private String gender;

    private String registrationNo;

    private List<Long> depIds;
    private String role;
    private Long placeId;
}
