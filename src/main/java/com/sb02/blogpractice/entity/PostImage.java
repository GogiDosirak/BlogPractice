package com.sb02.blogpractice.entity;

import java.util.UUID;

public class PostImage {
    private final UUID id;
    private UUID postId;
    private UUID imageId;

    public PostImage(UUID postId, UUID imageId) {
        this.id = UUID.randomUUID();
        this.postId = postId;
        this.imageId = imageId;
    }
}
