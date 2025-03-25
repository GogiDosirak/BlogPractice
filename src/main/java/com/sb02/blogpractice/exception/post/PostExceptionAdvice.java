package com.sb02.blogpractice.exception.post;

import com.sb02.blogpractice.exception.ResponseErrorBody;
import com.sb02.blogpractice.exception.user.UserNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PostExceptionAdvice {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(PostNotFound.class)
    public ResponseEntity<ResponseErrorBody> handlePostNotFound(PostNotFound e) {
        logger.error("{} handled by PostExceptionAdvice", e.getMessage());
        e.printStackTrace();

        return ResponseEntity.badRequest()
                .body(new ResponseErrorBody(e.getMessage()));
    }
}
