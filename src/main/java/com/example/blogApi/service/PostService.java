package com.example.blogApi.service;


import com.example.blogApi.entity.Post;

import java.util.List;

public interface PostService {

    Post savePost(Post post);

    List<Post> getAllPosts();
}
