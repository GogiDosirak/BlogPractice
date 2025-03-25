package com.sb02.blogpractice.repository;

import com.sb02.blogpractice.entity.PostImage;

import java.util.List;
import java.util.UUID;

public interface PostImageRepository {
    PostImage save(PostImage post);
    List<PostImage> findAll();
    List<PostImage> findByPostId(UUID postId);
    UUID deleteById(UUID id);
    void deleteByPostIdAndImageId(UUID postId, UUID imageId);
}
