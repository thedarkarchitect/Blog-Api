package com.example.blogApi.repository;

import com.example.blogApi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRespository extends JpaRepository<Post, Long> {

}
