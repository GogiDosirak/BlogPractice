package com.sb02.blogpractice.entity;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class Image implements Serializable {
    private static final long serialVersionUID = 1L;

    private final UUID id;
    private String originalName;
    private String extension;
    private String path;
    private Long size;
    private Instant updatedAt;

    @Builder
    public Image(String originalName, String extension, String path, Long size) {
        this.id = UUID.randomUUID();
        this.originalName = originalName;
        this.extension = extension;
        this.path = path;
        this.size = size;
        this.updatedAt = Instant.now();
    }
}
