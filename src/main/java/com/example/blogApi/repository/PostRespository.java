package com.example.blogApi.repository;

import com.example.blogApi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRespository extends JpaRepository<Post, Long> {
    List<Post> findAllByTitleContaining(String title);
}
