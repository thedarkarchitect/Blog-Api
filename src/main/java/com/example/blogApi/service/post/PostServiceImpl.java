package com.example.blogApi.service.post;

import com.example.blogApi.dto.requests.PostRequest;
import com.example.blogApi.entity.Post;
import com.example.blogApi.repository.PostRespository;
import com.example.blogApi.service.Likes.LikesService;
import com.example.blogApi.utils.AuthenticatedUserUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    //this is a dependency injection of the post repository into the service implementation
    private final PostRespository postRespository;
    private final AuthenticatedUserUtils authenticatedUserUtils;
    private final LikesService likesService;


    public Post savePost(PostRequest post) { //ok
        Post newPost = new Post();


        newPost.setTitle(post.getTitle());
        newPost.setContent(post.getContent());
        newPost.setViewCount(0);
        newPost.setLikeCount(0);
        newPost.setPostedBy(authenticatedUserUtils.getAuthenticatedUser().getName());
        newPost.setDate(new Date());

        return postRespository.save(newPost); //this creates a post
    }

    public List<Post> getAllPosts() {
        return postRespository.findAll();
    }

    @Override
    public Post getPostById(Long postId) {
        Optional<Post> optionalPost = postRespository.findById(postId);
        int count = likesService.countLikes(postId);
        System.out.println(count);
        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setViewCount(post.getViewCount() + 1);
            post.setLikeCount(count);
            return postRespository.save(post);
        } else {
            throw new EntityNotFoundException("Post not found");
        }
    }

    @Override
    public List<Post> searchByName(String name) {
        return postRespository.findAllByTitleContaining(name);
    }

    @Override
    public void deletePost(Long postId) {
        Optional<Post> optionalPost = postRespository.findById(postId);

        if(optionalPost.isPresent()) {
            postRespository.deleteById(postId);
        } else {
            throw new EntityNotFoundException("Post not found with id: "+ postId );
        }
    }

}