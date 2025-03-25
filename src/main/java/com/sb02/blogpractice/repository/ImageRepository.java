package com.sb02.blogpractice.repository;

import com.sb02.blogpractice.entity.Image;

import java.util.Optional;
import java.util.UUID;

public interface ImageRepository {
    Image save(Image image);
    Optional<Image> findById(UUID id);
    UUID deleteById(UUID id);
}
