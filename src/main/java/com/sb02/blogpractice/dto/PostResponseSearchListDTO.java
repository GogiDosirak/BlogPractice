package com.sb02.blogpractice.dto;

import java.util.List;

public record PostResponseSearchListDTO(
        List<PostResponseDTO> posts,
        int totalPages,
        int currentPage,
        String keyword
) {
}
