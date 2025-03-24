package com.sb02.blogpractice.entity;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class Image implements Serializable {
    private final UUID id;
    private String originalName;
    private String extension;
    private String path;
    private Long size;
    private Instant updatedAt;

    public Image(String originalName, String extension, String path, Long sizet) {
        this.id = UUID.randomUUID();
        this.originalName = originalName;
        this.extension = extension;
        this.path = path;
        this.size = size;
        this.updatedAt = Instant.now();
    }
}
