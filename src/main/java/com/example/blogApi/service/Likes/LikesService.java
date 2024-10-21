package com.example.blogApi.service.Likes;

import com.example.blogApi.entity.Likes;

public interface LikesService {
    Likes createLike(Long postId, Long userId);
    void RemoveLike(Long likeId);
    int countLikes(Long postId);
    Likes getLike(Long postId, Long userId);
}
