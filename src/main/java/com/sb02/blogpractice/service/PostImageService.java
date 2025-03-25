package com.sb02.blogpractice.service;

import com.sb02.blogpractice.dto.CreatePostImageRequestDTO;
import com.sb02.blogpractice.entity.PostImage;

import java.util.List;
import java.util.UUID;

public interface PostImageService {
    PostImage create(CreatePostImageRequestDTO createPostImageRequestDTO);
    List<PostImage> findByPostId(UUID postId);
    UUID deleteById(UUID id);
    void deleteByPostIdAndImageId(UUID postId, UUID imageId);
    void deleteByPostId(UUID postId);
}
