package com.sb02.blogpractice.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record PostResponseDTO(
        UUID id,
        String title,
        String content,
        String authorId,
        String nickname,
        List<String> tags,
        Instant createdAt
) {
}
