package com.unn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupRequest {
    private Long typeId;
    private String username;
    private String password;
    private String email;
}
