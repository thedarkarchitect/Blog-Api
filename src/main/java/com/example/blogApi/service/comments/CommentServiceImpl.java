package com.example.blogApi.service.comments;

import com.example.blogApi.dto.requests.CommentRequest;
import com.example.blogApi.entity.Comment;
import com.example.blogApi.entity.Post;
import com.example.blogApi.repository.CommentRepository;
import com.example.blogApi.repository.PostRespository;
import com.example.blogApi.utils.AuthenticatedUserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final PostRespository postRespository;
    private final AuthenticatedUserUtils authenticatedUserUtils;


    @Override
    public Comment createComment(Long postId, CommentRequest commentRequest) {
        //check this again
        Post post = postRespository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = new Comment();

        comment.setContent(commentRequest.getContent());
        comment.setPostedBy(authenticatedUserUtils.getAuthenticatedUser().getName());
        comment.setPost(post);
        comment.setCreateAt(new Date());

        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }
}
