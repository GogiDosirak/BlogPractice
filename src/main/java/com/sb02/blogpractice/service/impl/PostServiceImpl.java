package com.sb02.blogpractice.service.impl;

import com.sb02.blogpractice.dto.CreatePostRequestDTO;
import com.sb02.blogpractice.dto.PostResponseDTO;
import com.sb02.blogpractice.dto.PostResponseListDTO;
import com.sb02.blogpractice.dto.UpdatePostRequestDTO;
import com.sb02.blogpractice.entity.Post;
import com.sb02.blogpractice.entity.User;
import com.sb02.blogpractice.exception.post.PostNotFound;
import com.sb02.blogpractice.exception.user.UserNotFound;
import com.sb02.blogpractice.repository.PostRepository;
import com.sb02.blogpractice.repository.UserRepository;
import com.sb02.blogpractice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Post create(CreatePostRequestDTO createRequestPostDTO, String userId) {
        Post post = Post.builder()
                .authorId(userId)
                .title(createRequestPostDTO.title())
                .content(createRequestPostDTO.content())
                .tags(createRequestPostDTO.tags())
                .build();
        postRepository.save(post);
        return post;
    }

    @Override
    public Post findById(UUID id) {
        return postRepository.findById(id)
                .orElseThrow(() -> {
                    String errMessage = id + " 번 게시물이 존재하지 않습니다.";
                    logger.error(errMessage);
                    return new PostNotFound(errMessage);});
    }

    @Override
    public PostResponseListDTO findAll(int page, int size) {
        List<Post> postList = new ArrayList<>(postRepository.findAll());
        postList.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
        return postListToPageDTO(postList, page, size);
    }

    @Override
    public Post update(UUID id, UpdatePostRequestDTO updateRequestPostDTO) {
        Post post = findById(id);
        post.update(updateRequestPostDTO.title(), updateRequestPostDTO.content());
        return postRepository.save(post);
    }

    @Override
    public UUID deleteById(UUID id) {
        postRepository.deleteById(id);
        return id;
    }

    private PostResponseListDTO postListToPageDTO(List<Post> postList, int page, int size) {
        int start = (page-1) * size;
        int end = Math.min(start + size, postList.size());

        if (start > postList.size()) {
            return new PostResponseListDTO(Collections.emptyList(),0,0);
        }

        List<Post> pagingPostList =  postList.subList(start, end);

        List<PostResponseDTO> responseDTOList = pagingPostList.stream()
                .map(post -> entityToDTO(post))
                .toList();
        int totalPages = postList.size()%size == 0 ? postList.size()/size : postList.size()/size + 1;

        return new PostResponseListDTO(responseDTOList,totalPages,page);
    }


    private PostResponseDTO entityToDTO(Post post) {
        User user = userRepository.findById(post.getAuthorId())
                .orElseThrow(() -> {
                    String errMessage = post.getAuthorId() + " 유저를 찾을 수 없습니다.";
                    logger.error(errMessage);
                    return new UserNotFound(errMessage);
                });
        return new PostResponseDTO(post.getId(), post.getTitle(), post.getContent(), post.getAuthorId(), user.getNickname(), post.getTags(), post.getCreatedAt());
    }
}
