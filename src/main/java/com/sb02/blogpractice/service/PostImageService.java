package com.sb02.blogpractice.service;

import com.sb02.blogpractice.dto.CreatePostImageRequestDTO;
import com.sb02.blogpractice.entity.PostImage;

import java.util.UUID;

public interface PostImageService {
    PostImage create(CreatePostImageRequestDTO createPostImageRequestDTO);
    UUID deleteById(UUID id);
}
