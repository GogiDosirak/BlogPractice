package com.sb02.blogpractice.entity;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;

@Getter
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private String password;
    private String email;
    private String nickname;
    private Instant createdAt;

    @Builder
    public User(String id, String password, String email, String nickname) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.createdAt = Instant.now();
    }
}
