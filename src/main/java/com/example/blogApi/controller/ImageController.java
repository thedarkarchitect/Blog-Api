package com.example.blogApi.controller;

import com.example.blogApi.dto.ImageDTO;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageService imageService;
    private final PostRespository postRespository;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam MultipartFile file, @RequestParam Long postId) {
        try {
            Post post = postRespository.findById(postId).orElseThrow(() -> new Exception("Post not found"));
            ImageDTO savedImage = imageService.saveImages(file, post.getId());
            return ResponseEntity.ok(savedImage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/by_id/{imageId}")
    public ResponseEntity<?> getImageById(@PathVariable Long imageId) {
        try {
            return ResponseEntity.ok(imageService.getImageById(imageId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<byte[]> showImage(@PathVariable Long imageId) {
            Optional<Images> image = imageService.getImageById(imageId);
            if(image.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new byte[0]);
            }
            Images image1 = image.get();
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(image1.getType()))
                    .body(image1.getImageData());

    }

    @DeleteMapping("/delete/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable Long imageId) {
        try {
            imageService.deleteImage(imageId);
            return ResponseEntity.ok("Image deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
