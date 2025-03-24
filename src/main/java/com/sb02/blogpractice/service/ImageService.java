package com.sb02.blogpractice.service;

import com.sb02.blogpractice.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface ImageService {
    Image upload(MultipartFile file) throws IOException;
}
