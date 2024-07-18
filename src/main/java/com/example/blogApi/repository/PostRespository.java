package com.example.blogApi.repository;

import com.example.blogApi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRespository extends JpaRepository<Post, Long> {
    List<Post> findAllByNameContaining(String name);
}
