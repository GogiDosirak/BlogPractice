package com.sb02.blogpractice.repository;

import com.sb02.blogpractice.entity.Post;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepository {
    Post save(Post post);
    Optional<Post> findById(UUID id);
    List<Post> findAll();
    UUID deleteById(UUID id);
}
