package com.sb02.blogpractice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserRequestDTO(
        @NotNull
        @Size(min = 6, max = 30)
        String id,

        @NotNull
        @Size(min = 8, max = 20)
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,20}$",
                message = "비밀번호는 8~20자이며, 대문자, 소문자, 숫자, 특수문자(!@#$%^&*)를 각각 최소 1개 이상 포함해야 합니다."
        )
        String password,

        @NotNull
        @Email
        @Size(max = 100)
        String email,

        @NotNull
        @Size(min = 3, max = 50)
        String nickname

) {
}
