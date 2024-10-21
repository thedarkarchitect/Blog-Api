package com.example.blogApi.service.post;


import com.example.blogApi.dto.requests.PostRequest;
import com.example.blogApi.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    Post savePost(PostRequest post);

    List<Post> getAllPosts();

    Post getPostById(Long postId);

    List<Post> searchByName(String name);

    void deletePost(Long postId);
}
