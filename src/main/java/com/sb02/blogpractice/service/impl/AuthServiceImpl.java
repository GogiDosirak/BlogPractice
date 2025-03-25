package com.sb02.blogpractice.service.impl;

import com.sb02.blogpractice.dto.LoginParam;
import com.sb02.blogpractice.dto.UserDTO;
import com.sb02.blogpractice.entity.User;
import com.sb02.blogpractice.exception.user.UserNotAuthorized;
import com.sb02.blogpractice.service.AuthService;
import com.sb02.blogpractice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public UserDTO login(LoginParam loginParam) {
        User user = userService.findById(loginParam.id());
        if(!BCrypt.checkpw(loginParam.password(), user.getPassword())) {
            String errMessage = "비밀번호가 틀렸습니다.";
            logger.error(errMessage);
            throw new UserNotAuthorized(errMessage);
        }
        return entityToUserDTO(user);
    }

    private UserDTO entityToUserDTO(User user) {
        return new UserDTO(user.getId(), user.getEmail(), user.getNickname(), user.getCreatedAt());
    }
}
