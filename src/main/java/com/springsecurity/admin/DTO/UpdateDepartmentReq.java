package com.springsecurity.admin.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDepartmentReq {

    private String departmentName;
    private String description;
    private Boolean active;

}
