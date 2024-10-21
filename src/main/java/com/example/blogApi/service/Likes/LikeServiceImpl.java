package com.example.blogApi.service.Likes;

import com.example.blogApi.entity.Likes;
import com.example.blogApi.entity.Post;
import com.example.blogApi.entity.Users;
import com.example.blogApi.repository.LikeRepository;
import com.example.blogApi.repository.PostRespository;
import com.example.blogApi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikesService {

    private final PostRespository postRespository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;


    @Override
    public Likes createLike(Long postId, Long userId) {
        Post post = postRespository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Likes like = getLike(post.getId(), user.getId());
        Likes likes = new Likes();
        if(like == null) {
            likes.setPost(post);
            likes.setUser(user);
            likes.setCreatedAt(new Date());
            return likeRepository.save(likes);
        } else {
            throw new RuntimeException("Like already exists");
        }
    }

    @Override
    public void RemoveLike(Long likeId) {
        Likes like = likeRepository.findById(likeId).orElseThrow(() -> new RuntimeException("Like not found"));
        if(like != null) {
            likeRepository.delete(like);
        }
    }

    @Override
    public int countLikes(Long postId) {
        Post post = postRespository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        int count = 0;
        if(post != null) {
            List<Likes> likes = likeRepository.findByPostId(post.getId());
            count = likes.size();
            return count;
        } else {
            throw new RuntimeException("Post not found");
        }
    }

    @Override
    public Likes getLike(Long postId, Long userId) {
        Post post = postRespository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        return likeRepository.findLikesByPostIdAndUserId(post.getId(), user.getId()).orElse(null);
    }
}
