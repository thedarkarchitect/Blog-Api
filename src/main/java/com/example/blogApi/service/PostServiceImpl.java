package com.example.blogApi.service;

import com.example.blogApi.entity.Post;
import com.example.blogApi.repository.PostRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    @Autowired //this is a dependency injection of the post repository into the service implementation
    private PostRespository postRespository;

    public Post savePost(Post post) {
        post.setLikeCount(0);
        post.setViewCount(0);
        post.setDate(new Date());

        return postRespository.save(post); //this creates a post
    }

    public List<Post> getAllPosts() {
        return postRespository.findAll();
    }
}
