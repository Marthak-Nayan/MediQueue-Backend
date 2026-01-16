package com.springsecurity.admin.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorRecordsResp {

    private Long id;
    private String doctorName;
    private String role;
    private String username;
    private Long placeId;
    private List<String> departmentName;
}
