package com.sb02.blogpractice.dto;

public record LoginResponseDTO(
        UserDTO userDTO,
        String accessToken
) {
}
