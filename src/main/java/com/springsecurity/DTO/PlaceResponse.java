package com.springsecurity.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceResponse {

    private Long id;
    private String placename;
    private String email;
    private Long mobileno;
    private String address;
    private String username;
}
