package com.example.blogApi.service.images;

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

//    @Override
//    public Images saveImages(MultipartFile files, Long postId) throws IOException {
//        Optional<Post> post = Optional.ofNullable(postService.getPostById(postId));
//
//        Images newImage = new Images();
//
//        try {
//            Images image = new Images();
//            image.setName(files.getOriginalFilename());
//            image.setType(files.getContentType());
//            image.setImageData(files.getBytes());
//            post.ifPresent(image::setPost);
//
//            String buildDownloadUrl = "/api/v1/images/image/download/";
//            String downloadUrl = buildDownloadUrl + 0;
//            image.setDownloadUrl(downloadUrl);
//
//            Images savedImage = imageRepository.save(image);
//            System.out.println(savedImage.getId());
//            savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
//            imageRepository.save(savedImage);
//            System.out.println(savedImage.getId());
//
//            newImage.setId(savedImage.getId());
//            newImage.setName(savedImage.getName());
//            newImage.setDownloadUrl(savedImage.getDownloadUrl());
//            post.ifPresent(newImage::setPost);
//        } catch (IOException e) {
//            throw new IOException("Error saving image");
//        }
//
//        return newImage;
//    }

    @Override
    public Images saveImages(MultipartFile files, Long postId) throws IOException {
        Optional<Post> post = Optional.ofNullable(postService.getPostById(postId));

            Images image = new Images();
            image.setName(files.getOriginalFilename());
            image.setType(files.getContentType());
            image.setImageData(files.getBytes());
            post.ifPresent(image::setPost);

            Images savedImage = imageRepository.save(image);

            String buildDownloadUrl = "/api/v1/images/image/download/";
            savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
            imageRepository.save(savedImage);


        return savedImage;
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
    public Boolean deleteImage(Long imageId) {
        Optional<Images> image = getImageById(imageId);
        if(image.isPresent()) {
            imageRepository.deleteById(imageId);
            return true;
        } else {
            return false;
        }
    }


}
