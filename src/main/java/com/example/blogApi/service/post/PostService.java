package com.example.blogApi.service.post;


import com.example.blogApi.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    Post savePost(Post post);

    List<Post> getAllPosts();

    Post getPostById(Long postId);

    void likePost(Long postId);

    List<Post> searchByName(String name);

    void deletePost(Long postId);
}
