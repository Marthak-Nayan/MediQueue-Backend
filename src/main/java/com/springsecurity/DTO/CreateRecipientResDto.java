package com.springsecurity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRecipientResDto {
    private Long id;
    private String doctornane;
    private String username;
}
