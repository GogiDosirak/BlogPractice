package com.sb02.blogpractice.dto;

import java.util.List;

public record PostResponseListDTO(
        List<PostResponseDTO> posts,
        int totalPages,
        int currentPage
) {
}
