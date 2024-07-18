package com.example.blogApi.service;

import com.example.blogApi.entity.Comment;
import com.example.blogApi.entity.Post;
import com.example.blogApi.repository.CommentRepository;
import com.example.blogApi.repository.PostRespository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRespository postRespository;

    public Comment createComment(Long postId, String postedBy, String content) {
        Optional<Post> optionalPost = postRespository.findById(postId);
        if(optionalPost.isPresent()) {
            Comment comment = new Comment();

            comment.setPost(optionalPost.get());
            comment.setContent(content);
            comment.setPostedBy(postedBy);
            comment.setCreateAt(new Date());

            return commentRepository.save(comment);
        }

        throw new EntityNotFoundException("Post not found.");
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

}
