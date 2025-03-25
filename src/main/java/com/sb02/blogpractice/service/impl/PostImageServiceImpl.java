package com.sb02.blogpractice.service.impl;

import com.sb02.blogpractice.dto.CreatePostImageRequestDTO;
import com.sb02.blogpractice.entity.PostImage;
import com.sb02.blogpractice.repository.PostImageRepository;
import com.sb02.blogpractice.service.PostImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostImageServiceImpl implements PostImageService {
    private final PostImageRepository postImageRepository;

    @Override
    public PostImage create(CreatePostImageRequestDTO createPostImageRequestDTO) {
        PostImage postImage = DTOToEntity(createPostImageRequestDTO);
        postImageRepository.save(postImage);
        return postImage;
    }

    @Override
    public List<PostImage> findByPostId(UUID postId) {
        return postImageRepository.findByPostId(postId);
    }

    @Override
    public void deleteByPostIdAndImageId(UUID postId, UUID imageId) {
        postImageRepository.deleteByPostIdAndImageId(postId,imageId);
    }

    @Override
    public UUID deleteById(UUID id) {
        postImageRepository.deleteById(id);
        return id;
    }

    @Override
    public void deleteByPostId(UUID postId) {
        List<PostImage> postImageList = findByPostId(postId);
        postImageList.forEach(postImage -> {
            deleteByPostIdAndImageId(postId, postImage.getImageId());
        });
    }

    private PostImage DTOToEntity(CreatePostImageRequestDTO createPostImageRequestDTO) {
        return PostImage.builder()
                .imageId(createPostImageRequestDTO.imageId())
                .postId(createPostImageRequestDTO.postId())
                .build();
    }
}
