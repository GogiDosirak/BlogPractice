package com.sb02.blogpractice.controller;

import com.sb02.blogpractice.dto.*;
import com.sb02.blogpractice.entity.Post;
import com.sb02.blogpractice.entity.PostImage;
import com.sb02.blogpractice.entity.User;
import com.sb02.blogpractice.service.PostImageService;
import com.sb02.blogpractice.service.PostService;
import com.sb02.blogpractice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final PostImageService postImageService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(@Valid @RequestBody CreatePostRequestDTO createPostRequestDTO,
                                                      HttpServletRequest httpRequest) {
        validateTags(createPostRequestDTO);
        String userId = (String) httpRequest.getAttribute("userId");

        Post post = postService.create(createPostRequestDTO, userId);
        PostResponseDTO postResponseDTO = entityToDTO(post);

        // 프론트단에서 Content에 업로드된 이미지를 포함해서 내려준다고 가정?
        extractImageIdsFromContent(createPostRequestDTO.content())
                        .forEach(id -> postImageService.create(new CreatePostImageRequestDTO(post.getId(), id)));

        return ResponseEntity.ok(postResponseDTO);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDTO> findPostById(@PathVariable("postId") UUID postId) {
        Post post = postService.findById(postId);
        PostResponseDTO postResponseDTO = entityToDTO(post);
        return ResponseEntity.ok(postResponseDTO);
    }

    @GetMapping
    public ResponseEntity<PostResponseListDTO> findAll(@RequestParam(defaultValue = "1") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        PostResponseListDTO postResponseListDTO = postService.findAll(page, size);
        return ResponseEntity.ok(postResponseListDTO);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponseDTO> updatePost(@PathVariable("postId") UUID id,
                                                  @RequestBody UpdatePostRequestDTO updatePostRequestDTO) {
        // 작성자인지 확인은 PostAuthorizationInterceptor에서 진행
        Post post = postService.update(id, updatePostRequestDTO);
        return ResponseEntity.ok(entityToDTO(post));

    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<UUID> deletePost(@PathVariable("postId") UUID id) {

        // 작성자인지 확인은 PostAuthorizationInterceptor에서 진행
       postService.deleteById(id);
        return ResponseEntity.ok(id);
    }


    private void validateTags(CreatePostRequestDTO createPostRequestDTO) {
        List<String> tags = createPostRequestDTO.tags();

        for (String tag : tags) {
            if (tag == null || tag.isBlank()) {
                throw new IllegalArgumentException("태그는 공백일 수 없습니다.");
            }
            if (!tag.matches("^[a-zA-Z]+$")) {
                throw new IllegalArgumentException("태그는 영문자만 가능하고, 띄어쓰기를 포함할 수 없습니다: " + tag);
            }
        }
    }

    private List<UUID> extractImageIdsFromContent(String content) {
        List<UUID> imageUUIDs = new ArrayList<>();

        String uuidRegex = "\\{\\{([0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12})\\|[^}]+}}";
        Pattern pattern = Pattern.compile(uuidRegex);
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
                imageUUIDs.add(UUID.fromString(matcher.group(1)));
        }

        return imageUUIDs;
    }

    private PostResponseDTO entityToDTO(Post post) {
        User user = userService.findById(post.getAuthorId());
        return new PostResponseDTO(post.getId(), post.getTitle(), post.getContent(), post.getAuthorId(), user.getNickname(), post.getTags(), post.getCreatedAt());
    }


}
