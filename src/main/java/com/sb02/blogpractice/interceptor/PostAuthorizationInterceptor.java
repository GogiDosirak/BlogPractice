package com.sb02.blogpractice.interceptor;

import com.sb02.blogpractice.entity.Post;
import com.sb02.blogpractice.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class PostAuthorizationInterceptor implements HandlerInterceptor {
    private final PostService postService;

    public PostAuthorizationInterceptor(PostService postService) {
        this.postService = postService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();

        // 수정(PUT)과 삭제(DELETE) 요청만 검증
        if (method.equals("PUT") || method.equals("DELETE")) {
            String userId = (String) request.getAttribute("userId");

            // URL에서 postId 추출
            String postIdParam = request.getRequestURI().split("/api/posts/")[1];
            if (postIdParam != null) {
                UUID postId = UUID.fromString(postIdParam);
                Post post = postService.findById(postId);

                // 게시글 작성자와 현재 로그인한 사용자가 다른 경우 차단
                if (!userId.equals(post.getAuthorId())) {
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json");
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.getWriter().write("{\"error\":\"게시글 작성자가 아닙니다.\"}");
                    return false;
                }
            }
        }
        return true;
    }
}