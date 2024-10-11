package com.example.blogApi.dto;

import com.example.blogApi.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String name;
    private UserRole userRole;
}
