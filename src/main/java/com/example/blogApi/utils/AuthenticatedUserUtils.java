package com.example.blogApi.utils;

import com.example.blogApi.entity.Users;
import com.example.blogApi.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthenticatedUserUtils {

    private final UserRepository userRepository;

    public Users getAuthenticatedUser() {
        Users user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {

            Optional<Users> currentUser = userRepository.findFirstByEmail(userDetails.getUsername());

            if (currentUser.isPresent()) {
                user = currentUser.get();
            }
        }
        return user;
    }
}
