package com.example.blogApi.service.images;

import com.example.blogApi.dto.ImageDTO;
import com.example.blogApi.entity.Images;
import com.example.blogApi.entity.Post;
import com.example.blogApi.repository.ImageRepository;
import com.example.blogApi.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{

    private final ImageRepository imageRepository;
    private final PostService postService;

    @Override
    public ImageDTO saveImages(MultipartFile files, Long postId) throws IOException {
        Optional<Post> post = Optional.ofNullable(postService.getPostById(postId));

        ImageDTO savedImageDto = new ImageDTO();

        try {
            Images image = new Images();
            image.setName(files.getOriginalFilename());
            image.setType(files.getContentType());
            image.setImageData(files.getBytes());
            post.ifPresent(image::setPost);

            String buildDownloadUrl = "/api/v1/images/image/download/";
            String downloadUrl = buildDownloadUrl + 0;
            image.setDownloadUrl(downloadUrl);

            Images savedImage = imageRepository.save(image);
            System.out.println(savedImage.getId());
            savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
            imageRepository.save(savedImage);
            System.out.println(savedImage.getId());

            savedImageDto.setId(savedImage.getId());
            savedImageDto.setName(savedImage.getName());
            savedImageDto.setDownloadUrl(savedImage.getDownloadUrl());
            post.ifPresent(post1 -> savedImageDto.setPostId(post1.getId()));
        } catch (IOException e) {
            throw new IOException("Error saving image");
        }

        return savedImageDto;
    }

   @Override
public Optional<Images> getImageById(Long imageId) {
       return imageRepository.findById(imageId);
   }

    @Override
    public Images getImagesByPostId(Long postId) {
        return imageRepository.findByPostId(postId);
    }

    @Override
    public void deleteImage(Long imageId) {
        Optional<Images> image = getImageById(imageId);
        image.ifPresent(images -> imageRepository.deleteById(images.getId()));
    }


}
