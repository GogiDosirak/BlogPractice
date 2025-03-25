package com.sb02.blogpractice.dto;

import java.time.Instant;

public record UserDTO(
        String id,
        String email,
        String nickname,
        Instant createdAt
) {
}
