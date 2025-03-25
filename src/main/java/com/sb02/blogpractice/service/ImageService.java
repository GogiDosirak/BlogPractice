package com.sb02.blogpractice.service;

import com.sb02.blogpractice.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public interface ImageService {
    Image upload(MultipartFile file);
    Image findById(UUID id);
}
