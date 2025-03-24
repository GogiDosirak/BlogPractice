package com.sb02.blogpractice.dto;

import java.nio.file.Path;
import java.util.UUID;

public record ImageResponseDTO(
        UUID id,
        String path
) {
}
