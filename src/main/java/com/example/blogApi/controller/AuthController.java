package com.example.blogApi.controller;


import com.example.blogApi.dto.*;
import com.example.blogApi.dto.requests.AuthenticationRequest;
import com.example.blogApi.dto.requests.RefreshRequest;
import com.example.blogApi.dto.requests.SignUpRequest;
import com.example.blogApi.dto.responses.ApiResponse;
import com.example.blogApi.dto.responses.AuthenticationResponse;
import com.example.blogApi.dto.responses.MessageResponse;
import com.example.blogApi.entity.Users;
import com.example.blogApi.repository.UserRepository;
import com.example.blogApi.service.user.auth.AuthService;
import com.example.blogApi.service.user.jwt.UserServiceImpl;
import com.example.blogApi.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@CrossOrigin("*") // This annotation is used to handle the request from a different origin
public class AuthController {

    private final AuthService authService;

    private final JWTUtil jwtUtil;

    private final UserServiceImpl userService;

    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signupRequest) {
        if(authService.hasUserWithEmail(signupRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new MessageResponse("Email already exists"));
        }

        UserDTO userDto = authService.signup(signupRequest);
        if(userDto == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("success", userDto, 1));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        Optional<Users> user = userRepository.findFirstByEmail(authenticationRequest.getEmail());
        if(user.isPresent()) {
            UserDetails details = userService.loadUserByUsername(user.get().getEmail());

            String refreshToken = jwtUtil.generateRefreshToken(details.getUsername());
            String accessToken = jwtUtil.generateAccessToken(details.getUsername());

            if(refreshToken != null && accessToken != null) {
                AuthenticationResponse response = new AuthenticationResponse();
                response.setJwtAccessToken(accessToken);
                response.setJwtRefreshToken(refreshToken);
                response.setUserRole(user.get().getUserRole());
                response.setUserId(user.get().getId());
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", response, 1));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Invalid credentials"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Invalid credentials"));
        }

    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest refreshToken) {
        if(!jwtUtil.isTokenValid(refreshToken.getRefreshToken())) {
            return ResponseEntity.status(401).body(new MessageResponse("Refresh token is expired"));
        }

        String email = jwtUtil.extractUsername(refreshToken.getRefreshToken());
        Optional<Users> optionalUser = userRepository.findFirstByEmail(email);
        String accessToken = jwtUtil.generateAccessToken(email);

        AuthenticationResponse response = new AuthenticationResponse();

        if(optionalUser.isPresent()){
            response.setJwtRefreshToken(refreshToken.getRefreshToken());
            response.setJwtAccessToken(accessToken);
            response.setUserRole(optionalUser.get().getUserRole());
            response.setUserId(optionalUser.get().getId());
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", response, 1));
    }
}
