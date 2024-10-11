package com.example.blogApi.controller;

import com.example.blogApi.dto.requests.CommentRequest;
import com.example.blogApi.dto.responses.ApiResponse;
import com.example.blogApi.dto.responses.MessageResponse;
import com.example.blogApi.entity.Comment;
import com.example.blogApi.service.comments.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
@CrossOrigin("*")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/createComment")
    public ResponseEntity<?> createComment(@RequestParam Long postId, @RequestBody CommentRequest commentRequest) {
        try {
            Comment createdComment = commentService.createComment(postId, commentRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("success", createdComment, 1));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Failed to create comment."));
        }
    }

    @GetMapping("/by_post/{postId}")
    public ResponseEntity<?> getCommentsByPostId(@PathVariable Long postId) {
        try {
            List<Comment> comments = commentService.getCommentsByPostId(postId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", comments, comments.size()));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("No comments found for post with id " + postId));
        }
    }
}
