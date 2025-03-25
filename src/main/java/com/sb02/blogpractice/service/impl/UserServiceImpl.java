package com.sb02.blogpractice.service.impl;

import com.sb02.blogpractice.dto.CreateUserRequestDTO;
import com.sb02.blogpractice.dto.UserDTO;
import com.sb02.blogpractice.entity.User;
import com.sb02.blogpractice.repository.UserRepository;
import com.sb02.blogpractice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDTO create(CreateUserRequestDTO createRequestUserDTO) {
        checkDuplicateId(createRequestUserDTO.id());
        User user = createUserEntity(createRequestUserDTO);
        userRepository.save(user);
        return entityToUserDTO(user);
    }

    @Override
    public User findById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 유저를 찾을 수 없습니다."));
        return user;
    }

    @Override
    public boolean existsById(String id) {
        return userRepository.findById(id).isPresent();
    }

    private void checkDuplicateId(String id) {
        if(existsById(id)) {
            throw new IllegalStateException("중복된 아이디 입니다.");
        }
    }


    private User createUserEntity(CreateUserRequestDTO createRequestUserDTO) {
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
