package com.sb02.blogpractice.service;

import com.sb02.blogpractice.dto.LoginParam;
import com.sb02.blogpractice.dto.UserDTO;

public interface AuthService {
    UserDTO login(LoginParam loginParam);

}
