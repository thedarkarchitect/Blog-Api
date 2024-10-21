package com.example.blogApi.controller;

import com.example.blogApi.dto.responses.LikesCount;
import com.example.blogApi.entity.Likes;
import com.example.blogApi.entity.Post;
import com.example.blogApi.repository.PostRespository;
import com.example.blogApi.service.Likes.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/likes")
public class LikesController {
    private final LikesService likesService;
    private final PostRespository postRespository;

    @PostMapping("/create")
    public ResponseEntity<?> createLike(@RequestParam Long postId, @RequestParam Long userId) {
        Likes like = likesService.createLike(postId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(like);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeLike(@RequestParam Long postId, @RequestParam Long userId) {
        Likes like = likesService.getLike(postId, userId);
        likesService.RemoveLike(like.getId());
        return ResponseEntity.ok().body("Like removed successfully");
    }

    @GetMapping("/like_count")
    public ResponseEntity<LikesCount> countLikes(@RequestParam Long postId) {
        int count = likesService.countLikes(postId);
        return ResponseEntity.ok().body(new LikesCount(count));
    }
}
