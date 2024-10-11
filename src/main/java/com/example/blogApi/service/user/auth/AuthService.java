package com.example.blogApi.service.user.auth;

import com.example.blogApi.dto.requests.SignUpRequest;
import com.example.blogApi.dto.UserDTO;
import com.example.blogApi.entity.Users;

public interface AuthService {
    Boolean hasUserWithEmail(String email);

    UserDTO signup(SignUpRequest signupRequest);

    UserDTO convertToUserDTO(Users user);
}
