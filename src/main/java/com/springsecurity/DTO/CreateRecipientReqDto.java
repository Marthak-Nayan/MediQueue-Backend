package com.springsecurity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRecipientReqDto {

    private String recipientName;
    private String username;
    private String password;
    private String role;

}
