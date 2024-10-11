package com.example.blogApi.repository;

import com.example.blogApi.entity.Users;
import com.example.blogApi.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findFirstByEmail(String email);

    Optional<Users> findByUserRole(UserRole userRole);
}
