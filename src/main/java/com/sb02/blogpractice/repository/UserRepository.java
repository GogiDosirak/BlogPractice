package com.sb02.blogpractice.repository;

import com.sb02.blogpractice.entity.User;

import java.util.Optional;


public interface UserRepository {
    User save(User user);
    Optional<User> findById(String id);
}
