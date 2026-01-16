package com.springsecurity.admin.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateDepartmentResp {

    private Long id;
    private String departmentName;
    private String description;
    private Boolean active;
    private Long placeId;
    private String placeName;
}
