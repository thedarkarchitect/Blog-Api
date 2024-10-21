package com.example.blogApi.repository;

import com.example.blogApi.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Likes, Long> {
    List<Likes> findByPostId(Long postId);
    Optional<Likes> findLikesByPostIdAndUserId(Long postId, Long userId);
}
