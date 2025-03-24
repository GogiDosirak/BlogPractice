package com.sb02.blogpractice.controller;

import com.sb02.blogpractice.dto.LoginParam;
import com.sb02.blogpractice.dto.LoginResponseDTO;
import com.sb02.blogpractice.dto.UserDTO;
import com.sb02.blogpractice.jwt.JwtUtil;
import com.sb02.blogpractice.service.AuthService;
import com.sb02.blogpractice.service.UserService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginParam loginParam) {
        UserDTO userDTO =  authService.login(loginParam);
        String accessToken = jwtUtil.generateToken(userDTO.id());
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(userDTO, accessToken);
        return ResponseEntity.ok(loginResponseDTO);
    }
}
