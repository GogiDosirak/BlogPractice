package com.sb02.blogpractice.entity;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@Getter
public class PostImage implements Serializable {
    private final UUID id;
    private UUID postId;
    private UUID imageId;

    @Builder
    public PostImage(UUID postId, UUID imageId) {
        this.id = UUID.randomUUID();
        this.postId = postId;
        this.imageId = imageId;
    }
}
