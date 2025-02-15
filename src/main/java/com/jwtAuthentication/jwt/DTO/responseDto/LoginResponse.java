package com.jwtAuthentication.jwt.DTO.responseDto;

import com.jwtAuthentication.jwt.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private User user;
    private String role;
}
