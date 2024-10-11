package com.example.blogApi.service.comments;

import com.example.blogApi.dto.requests.CommentRequest;
import com.example.blogApi.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(Long postId, CommentRequest commentRequest);

    List<Comment> getCommentsByPostId(Long postId);

//    Boolean deleteComment(Long commentId);
}
