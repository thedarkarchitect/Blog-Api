package com.example.blogApi.dto;

import com.example.blogApi.entity.Comment;
import com.example.blogApi.entity.Images;
import lombok.Data;

import java.util.List;

@Data
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String createdDate;
    private String updatedDate;
    private int likes;
    private int dislikes;
    private List<Comment> comments;
    private Images image;
}
