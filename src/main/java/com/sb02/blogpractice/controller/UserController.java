package com.sb02.blogpractice.controller;

import com.sb02.blogpractice.dto.CreateRequestUserDTO;
import com.sb02.blogpractice.dto.UserDTO;
import com.sb02.blogpractice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody CreateRequestUserDTO createRequestUserDTO) {
        UserDTO userDTO = userService.create(createRequestUserDTO);
        return ResponseEntity.ok(userDTO);
    }



}
