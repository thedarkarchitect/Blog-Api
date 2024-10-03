package com.example.blogApi.service.post;

import com.example.blogApi.entity.Post;
import com.example.blogApi.repository.PostRespository;
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
//    private final ImageRepository imageRepository;


    public Post savePost(Post post) { //ok
        post.setLikeCount(0);
        post.setViewCount(0);
        post.setDate(new Date());
        return postRespository.save(post); //this creates a post
    }

    public List<Post> getAllPosts() {
        return postRespository.findAll();
    }

    @Override
    public Post getPostById(Long postId) {
//        return postRespository.findById(postId);
        Optional<Post> optionalPost = postRespository.findById(postId);

        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setViewCount(post.getViewCount() + 1);
            return postRespository.save(post);
        } else {
            throw new EntityNotFoundException("Post not found");
        }

    }

    @Override
    public void likePost(Long postId) {
        Optional<Post> optionalPost = postRespository.findById(postId);

        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();

            post.setLikeCount(post.getLikeCount() + 1);
            postRespository.save(post);
        } else {
            throw new EntityNotFoundException("Post not found with id: "+ postId );
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