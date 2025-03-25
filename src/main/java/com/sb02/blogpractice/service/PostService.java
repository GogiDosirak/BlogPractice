package com.sb02.blogpractice.service;

import com.sb02.blogpractice.dto.*;
import com.sb02.blogpractice.entity.Post;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

public interface PostService {
    PostResponseDTO create(CreatePostRequestDTO createRequestPostDTO, String userId);
    Post findById(UUID id);
    PostResponseListDTO findAll(int page, int size);
    PostResponseSearchListDTO findByTitle(String title, int page, int size);
    PostResponseSearchListDTO findByContent(String content, int page, int size);
    PostResponseSearchListDTO findByTag(String tag, int page, int size);
    Post update(UUID id, UpdatePostRequestDTO updateRequestPostDTO);
    UUID deleteById(UUID id);
}
