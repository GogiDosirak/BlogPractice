package com.sb02.blogpractice.entity;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
public class Post implements Serializable {
    private final UUID id;
    private String title;
    private String content;
    private String authorId;
    private List<String> tags;
    private Instant createdAt;
    private Instant updatedAt;

    @Builder
    public Post(String title, String content, String authorId, List<String> tags) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.tags = tags;
        this.createdAt = Instant.now();
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.updatedAt = Instant.now();
    }
}
