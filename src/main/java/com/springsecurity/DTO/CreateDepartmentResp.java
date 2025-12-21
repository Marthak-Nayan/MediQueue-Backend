package com.springsecurity.DTO;

import com.springsecurity.entities.PlaceName;
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
