package com.springsecurity.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceUpdateReq {
    private String placename;
    private String email;
    private Long mobileNo;
    private String address;
}
