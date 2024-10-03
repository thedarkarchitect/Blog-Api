package com.example.blogApi.service.comments;

import com.example.blogApi.entity.Comment;
import com.example.blogApi.entity.Post;
import com.example.blogApi.repository.CommentRepository;
import com.example.blogApi.repository.PostRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final PostRespository postRespository;


    @Override
    public Comment createComment(Long postId, String postedBy, String content) {
        //check this again
            Post post = postRespository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));


                Comment comment = new Comment();

                comment.setPost(post);
                comment.setPostedBy(postedBy);
                comment.setContent(content);
                comment.setCreateAt(new Date());

                return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }
}
