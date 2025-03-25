package com.sb02.blogpractice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreatePostRequestDTO(
        String title,

        @NotBlank(message = "내용은 비어 있을 수 없습니다.")
        @Size(min = 2, max = 1000, message = "내용은 2자 이상 1000자 이하여야 합니다.")
        String content,

        List<String> tags
) {
}
