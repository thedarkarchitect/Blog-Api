package com.example.blogApi.dto.responses;

import com.example.blogApi.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse {
    private String jwtAccessToken;

    private String jwtRefreshToken;

    private Long userId;

    private UserRole userRole;
}
