package com.sb02.blogpractice.repository;

import com.sb02.blogpractice.entity.PostImage;

import java.util.UUID;

public interface PostImageRepository {
    PostImage save(PostImage post);
    UUID deleteById(UUID id);
}
