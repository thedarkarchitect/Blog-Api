package com.example.blogApi.controller;

import com.example.blogApi.entity.Post;
import com.example.blogApi.repository.PostRespository;
import com.example.blogApi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody Post post) {
        try {
            Post createdPost = postService.savePost(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Post>> getPosts() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Long postId) {
        try {
            Post getPost = postService.getPostById(postId);
            return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(postId));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable Long postId) {
        try {
            postService.likePost(postId);
            return ResponseEntity.ok(new String[]{"Post liked successfully."});
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchByName(@PathVariable String name) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(postService.searchByName(name));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
