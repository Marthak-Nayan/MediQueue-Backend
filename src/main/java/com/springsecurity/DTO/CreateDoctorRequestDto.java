package com.springsecurity.DTO;

import com.springsecurity.entities.Speciality;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDoctorRequestDto {

    private Long placeId;
    private String doctorName;
    private String username;
    private String password;
    private List<Speciality> speciality;
    private String role;
}
