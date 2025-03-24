package com.sb02.blogpractice.service;

import com.sb02.blogpractice.dto.LoginParam;
import com.sb02.blogpractice.dto.UserDTO;
import com.sb02.blogpractice.entity.User;
import com.sb02.blogpractice.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;

    @Override
    public UserDTO login(LoginParam loginParam) {
        User user = userService.findById(loginParam.id());
        if(!BCrypt.checkpw(loginParam.password(), user.getPassword())) {
            throw new IllegalStateException("비밀번호가 틀립니다.");
        }
        return entityToUserDTO(user);
    }

    private UserDTO entityToUserDTO(User user) {
        return new UserDTO(user.getId(), user.getEmail(), user.getNickname(), user.getCreatedAt());
    }
}
