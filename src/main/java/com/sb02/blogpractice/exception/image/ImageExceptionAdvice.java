package com.sb02.blogpractice.exception.image;

import com.sb02.blogpractice.exception.ResponseErrorBody;
import com.sb02.blogpractice.exception.post.PostNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ImageExceptionAdvice {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(ImageNotFound.class)
    public ResponseEntity<ResponseErrorBody> handleImageNotFound(ImageNotFound e) {
        logger.error("{} handled by ImageExceptionAdvice", e.getMessage());
        e.printStackTrace();

        return ResponseEntity.badRequest()
                .body(new ResponseErrorBody(e.getMessage()));
    }
}
