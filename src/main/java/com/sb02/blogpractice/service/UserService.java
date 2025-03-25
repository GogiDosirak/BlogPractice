package com.sb02.blogpractice.service;

import com.sb02.blogpractice.dto.CreateUserRequestDTO;
import com.sb02.blogpractice.dto.UserDTO;
import com.sb02.blogpractice.entity.User;

public interface UserService {
    UserDTO create(CreateUserRequestDTO createRequestUserDTO);
    User findById(String id);
    boolean existsById(String id);

}
