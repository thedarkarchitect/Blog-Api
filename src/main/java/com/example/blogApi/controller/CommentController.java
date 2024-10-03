package com.example.blogApi.controller;

import com.example.blogApi.entity.Comment;
import com.example.blogApi.service.comments.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/createComment")
    public ResponseEntity<?> createComment(@RequestParam Long postId,
                                           @RequestParam String content,
                                           @RequestParam String postedBy) {
        try {
            Comment createdComment = commentService.createComment(postId, postedBy, content);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/by_post/{postId}")
    public ResponseEntity<?> getCommentsByPostId(@PathVariable Long postId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByPostId(postId));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
