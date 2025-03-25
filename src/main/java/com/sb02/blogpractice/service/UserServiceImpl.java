package com.sb02.blogpractice.service;

import com.sb02.blogpractice.dto.CreateRequestUserDTO;
import com.sb02.blogpractice.dto.UserDTO;
import com.sb02.blogpractice.entity.User;
import com.sb02.blogpractice.exception.user.UserDuplicated;
import com.sb02.blogpractice.exception.user.UserNotFound;
import com.sb02.blogpractice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public UserDTO create(CreateRequestUserDTO createRequestUserDTO) {
        checkDuplicateId(createRequestUserDTO.id());
        User user = createUserEntity(createRequestUserDTO);
        userRepository.save(user);
        return entityToUserDTO(user);
    }

    @Override
    public User findById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    String errMessage = id + " 유저를 찾을 수 없습니다.";
                    logger.error(errMessage);
                    return new UserNotFound(errMessage);
                });
        return user;
    }

    @Override
    public boolean existsById(String id) {
        return userRepository.findById(id).isPresent();
    }

    private void checkDuplicateId(String id) {
        if (existsById(id)) {
            String errMessage = id + " 는 이미 존재하는 userId 입니다.";
            logger.error(errMessage);
            throw new UserDuplicated(errMessage);
        }
    }


    private User createUserEntity(CreateRequestUserDTO createRequestUserDTO) {
        String hashedPassword = BCrypt.hashpw(createRequestUserDTO.password(), BCrypt.gensalt());
        return User.builder()
                .id(createRequestUserDTO.id())
                .password(hashedPassword)
                .nickname(createRequestUserDTO.nickname())
                .email(createRequestUserDTO.email())
                .build();
    }

    private UserDTO entityToUserDTO(User user) {
        return new UserDTO(user.getId(), user.getEmail(), user.getNickname(), user.getCreatedAt());
    }
}
