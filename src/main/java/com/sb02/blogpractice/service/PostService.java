package com.sb02.blogpractice.service;

import com.sb02.blogpractice.dto.CreatePostRequestDTO;
import com.sb02.blogpractice.dto.PostResponseListDTO;
import com.sb02.blogpractice.dto.UpdatePostRequestDTO;
import com.sb02.blogpractice.entity.Post;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

public interface PostService {
    Post create(CreatePostRequestDTO createRequestPostDTO, String userId);
    Post findById(UUID id);
    PostResponseListDTO findAll(int page, int size);
    Post update(UUID id, UpdatePostRequestDTO updateRequestPostDTO);
    UUID deleteById(UUID id);
}
