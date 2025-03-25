package com.sb02.blogpractice.config;

import com.sb02.blogpractice.interceptor.JwtAuthInterceptor;
import com.sb02.blogpractice.interceptor.PostAuthorizationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final JwtAuthInterceptor jwtAuthInterceptor;
    private final PostAuthorizationInterceptor postAuthorizationInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtAuthInterceptor)
                .addPathPatterns("/api/posts/**", "/api/images/**") // 인증이 필요한 경로 패턴
                .excludePathPatterns() // GET 요청 제외 (필요에 따라 조정)
                .excludePathPatterns("/api/users/register", "/api/auth/login"); // 인증 제외 경로

        registry.addInterceptor(postAuthorizationInterceptor) // 게시글 수정/삭제 시 추가 검증
                .addPathPatterns("/api/posts/**");
    }
}