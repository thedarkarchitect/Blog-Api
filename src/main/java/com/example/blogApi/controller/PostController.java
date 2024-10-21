package com.example.blogApi.controller;

import com.example.blogApi.dto.requests.PostRequest;
import com.example.blogApi.dto.responses.ApiResponse;
import com.example.blogApi.dto.responses.MessageResponse;
import com.example.blogApi.entity.Post;
import com.example.blogApi.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@CrossOrigin("*")
public class PostController {

    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody PostRequest post) {
        try {
            Post createdPost = postService.savePost(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("success", createdPost, 1));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Failed to create post."));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getPosts() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", postService.getAllPosts(), postService.getAllPosts().size()));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Failed to fetch posts."));
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Long postId) {
        try {
            return ResponseEntity.ok(new ApiResponse("success", postService.getPostById(postId), 1));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Post with id " + postId + " not found."));
        }
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchByName(@PathVariable String name) {
        try {
            return ResponseEntity.ok(new ApiResponse("success", postService.searchByName(name), postService.searchByName(name).size()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Failed to find post with search term: " + name));
        }
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        try {
            postService.deletePost(postId);
            return ResponseEntity.ok(new MessageResponse("Post deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
