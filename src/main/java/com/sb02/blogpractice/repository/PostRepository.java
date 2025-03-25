package com.sb02.blogpractice.repository;

import com.sb02.blogpractice.entity.Post;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepository {
    Post save(Post post);
    Optional<Post> findById(UUID id);
    List<Post> findAll();
    List<Post> findByTitle(String title);
    List<Post> findByContent(String content);
    List<Post> findByTag(String tag);
    UUID deleteById(UUID id);
}
