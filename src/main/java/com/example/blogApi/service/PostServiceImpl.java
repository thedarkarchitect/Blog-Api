package com.example.blogApi.service;

import com.example.blogApi.entity.Post;
import com.example.blogApi.repository.PostRespository;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.action.internal.EntityActionVetoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public Post getPostById(Long postId) {
        Optional<Post> optionalPost = postRespository.findById(postId);

        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setViewCount(post.getViewCount() + 1);
            return postRespository.save(post);
        } else {
            throw new EntityNotFoundException("Post not found");
        }
    }

    public void likePost(Long postId) {
        Optional<Post> optionalPost = postRespository.findById(postId);

        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();

            post.setLikeCount(post.getLikeCount() + 1);
            postRespository.save(post);
        } else {
            throw new EntityNotFoundException("Post not found with id: "+ postId );
        }

    }
}
