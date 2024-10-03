package com.example.blogApi.service.images;

import com.example.blogApi.dto.ImageDTO;
import com.example.blogApi.entity.Images;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface ImageService {
    ImageDTO saveImages(MultipartFile files, Long postId) throws IOException;
    Optional<Images> getImageById(Long imageId);
    Images getImagesByPostId(Long postId);
    void deleteImage(Long imageId);
}
