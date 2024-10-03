package com.example.blogApi.repository;

import com.example.blogApi.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ImageRepository extends JpaRepository<Images, Long> {
    Images findByPostId(Long postId);
}
