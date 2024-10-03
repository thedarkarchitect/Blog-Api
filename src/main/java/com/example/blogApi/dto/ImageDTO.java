package com.example.blogApi.dto;

import lombok.Data;

@Data
public class ImageDTO {
    private Long id;
    private String name;
    private String type;
    private String downloadUrl;
    private Long postId;
}
