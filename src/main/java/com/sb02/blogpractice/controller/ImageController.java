package com.sb02.blogpractice.controller;

import com.sb02.blogpractice.dto.CreatePostImageRequestDTO;
import com.sb02.blogpractice.dto.ImageResponseDTO;
import com.sb02.blogpractice.entity.Image;
import com.sb02.blogpractice.entity.PostImage;
import com.sb02.blogpractice.service.ImageService;
import com.sb02.blogpractice.service.PostImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {
    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<ImageResponseDTO> upload(@RequestParam MultipartFile file) throws IOException {
        validateFile(file);
        Image image = imageService.upload(file);
        ImageResponseDTO imageResponseDTO = entityToDTO(image);

        return ResponseEntity.ok(imageResponseDTO);
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<ImageResponseDTO> getImage(@PathVariable("imageId") UUID id) {
        Image image = imageService.findById(id);
        ImageResponseDTO imageResponseDTO = entityToDTO(image);

        return ResponseEntity.ok(imageResponseDTO);
    }

    private void validateFile(MultipartFile multipartFile) {
        String originalName = multipartFile.getOriginalFilename();
        if(!isAllowedExtension(originalName)) {
            throw new IllegalStateException("jpg, jpeg, png, gif 파일만 가능합니다.");
        }
        if(multipartFile.getSize() > 1_048_576) {
            throw new IllegalStateException("1MB 이하의 파일만 업로드할 수 있습니다.");
        }
        String nameWithoutExtension = originalName.substring(0, originalName.lastIndexOf("."));
        if(nameWithoutExtension.length() > 32) {
            throw new IllegalStateException("32자 이내의 파일명을 가진 파일만 업로드 할 수 있습니다.");
        }
    }

    private ImageResponseDTO entityToDTO(Image image) {
        return new ImageResponseDTO(image.getId(), image.getPath());
    }

    private boolean isAllowedExtension(String originalFilename) {
        String[] allowedExtensions = { ".png", ".jpg", ".jpeg", ".gif"};
        if (originalFilename == null) return false;

        String lowercaseName = originalFilename.toLowerCase();
        for (String ext : allowedExtensions) {
            if (lowercaseName.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }
}
