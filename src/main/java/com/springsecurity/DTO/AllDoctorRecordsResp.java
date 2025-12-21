package com.springsecurity.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllDoctorRecordsResp {

    private Long id;
    private String doctorName;
    private String role;
    private String username;
    private Long placeId;
    private List<String> departmentName;
}
