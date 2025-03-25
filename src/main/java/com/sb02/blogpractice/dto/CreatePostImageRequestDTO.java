package com.sb02.blogpractice.dto;

import java.util.UUID;

public record CreatePostImageRequestDTO(
        UUID postId,
        UUID imageId
) {
}
