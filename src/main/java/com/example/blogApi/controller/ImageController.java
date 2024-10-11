package com.example.blogApi.controller;

import com.example.blogApi.dto.responses.ApiResponse;
import com.example.blogApi.dto.responses.MessageResponse;
import com.example.blogApi.entity.Images;
import com.example.blogApi.entity.Post;
import com.example.blogApi.repository.PostRespository;
import com.example.blogApi.service.images.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
@CrossOrigin("*")
public class ImageController {

    private final ImageService imageService;
    private final PostRespository postRespository;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam MultipartFile file, @RequestParam Long postId) {
        try {
            Post post = postRespository.findById(postId).orElseThrow(() -> new Exception("Post not found"));
            System.out.println(post.getId());
            Images savedImage = imageService.saveImages(file, post.getId());
            return ResponseEntity.ok(new ApiResponse("success", savedImage, 1));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Failed to upload image"));
        }
    }

    @GetMapping("/by_id/{imageId}")
    public ResponseEntity<?> getImageById(@PathVariable Long imageId) {
        Optional<Images> image = imageService.getImageById(imageId);
        if(image.isPresent()){
            return ResponseEntity.ok(new ApiResponse("success", image.get(), 1));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Image not found"));
        }
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<?> showImage(@PathVariable Long imageId) {
        Optional<Images> image = imageService.getImageById(imageId);
        if(image.isPresent()) {
            Images image1 = image.get();
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(image1.getType()))
                    .body(image1.getImageData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Image not found"));
        }
    }

    @DeleteMapping("/delete/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable Long imageId) {

        Boolean deleted = imageService.deleteImage(imageId);
        if(deleted) {
            return ResponseEntity.ok(new MessageResponse("Image deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Image not found"));
        }
    }

}
