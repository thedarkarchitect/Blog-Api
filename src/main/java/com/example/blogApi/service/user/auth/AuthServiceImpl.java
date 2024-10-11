package com.example.blogApi.service.user.auth;

import com.example.blogApi.config.ModelMapperConfig;
import com.example.blogApi.dto.requests.SignUpRequest;
import com.example.blogApi.dto.UserDTO;
import com.example.blogApi.entity.Users;
import com.example.blogApi.enums.UserRole;
import com.example.blogApi.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ModelMapperConfig modelMapperConfig;

    @PostConstruct
    public void createAdminAccount() {
        Optional<Users> optionalAdmin = userRepository.findByUserRole(UserRole.ADMIN);

        if(optionalAdmin.isEmpty()) {
            Users admin = new Users();
            admin.setName("admin");
            admin.setEmail("admin@test.com");
            admin.setUserRole(UserRole.ADMIN);
            admin.setPassword(new BCryptPasswordEncoder().encode(System.getenv("ADMIN_PASSWORD")));
            userRepository.save(admin);
            System.out.println("Admin account created successfully");
        } else {
            System.out.println("Admin account already exists!");
        }
    }


    @Override
    public Boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @Override
    public UserDTO signup(SignUpRequest signupRequest) {
        Users user = new Users();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setUserRole(UserRole.DEVELOPER);
        userRepository.save(user);
        return convertToUserDTO(user);
    }

    @Override
    public UserDTO convertToUserDTO(Users user) {
        return modelMapperConfig.modelMapper().map(user, UserDTO.class);
    }
}
