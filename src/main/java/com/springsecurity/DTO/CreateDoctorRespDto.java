package com.springsecurity.DTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CreateDoctorRespDto {

    private Long id;
    private String doctornane;
    private String username;
}
