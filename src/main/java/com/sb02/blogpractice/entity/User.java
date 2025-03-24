package com.sb02.blogpractice.entity;

import java.time.Instant;

public class User {
    private final String id;
    private String password;
    private String email;
    private String nickname;
    private Instant createdAt;

    public User(String id, String password, String email, String nickname) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.createdAt = Instant.now();
    }
}
