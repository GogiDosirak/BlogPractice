package com.sb02.blogpractice.scheduler;

import com.sb02.blogpractice.entity.Image;
import com.sb02.blogpractice.repository.ImageRepository;
import com.sb02.blogpractice.repository.PostImageRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ImageCleanUpScheduler {
    private final ImageRepository imageRepository;
    private final PostImageRepository postImageRepository;
    private static final Logger logger = LoggerFactory.getLogger(ImageCleanUpScheduler.class);

    @Scheduled(fixedRate = 60000)
    public void cleanUnusedImages() {
        logger.info("미사용 이미지 정리 시작...");

        // 사용 중인 imageId 목록 조회 (PostImage에서 관리하는 이미지)
        Set<UUID> usedImageIds = postImageRepository.findAll().stream()
                .map(postImage -> postImage.getImageId())
                .collect(Collectors.toSet());

        // 모든 이미지 중에서 사용되지 않은 이미지 찾기 (PostImage에서 관리되지 않는 이미지)
        List<Image> unusedImages = imageRepository.findAll().stream()
                .filter(image -> !usedImageIds.contains(image.getId()))
                .toList();

        if (unusedImages.isEmpty()) {
            logger.info("정리할 미사용 이미지가 없습니다.");
            return;
        }

        // 미사용 이미지 삭제
        unusedImages.forEach(image -> {
            try {
                // 실제 이미지 파일 삭제 (data/imageFiles/{imageFileName})
                Path imageFilePath = Paths.get(image.getPath());
                if (Files.exists(imageFilePath)) {
                    Files.delete(imageFilePath);
                    logger.info("삭제된 이미지 파일: {}", imageFilePath);
                } else {
                    logger.warn("️이미지 파일을 찾을 수 없음: {}", imageFilePath);
                }

                // 직렬화된 Image 엔티티 파일 삭제 (data/image/{imageId}.ser)
                Path serializedImagePath = Paths.get(System.getProperty("user.dir"), "data", "image", image.getId() + ".ser");
                if (Files.exists(serializedImagePath)) {
                    Files.delete(serializedImagePath);
                    logger.info("삭제된 직렬화된 이미지 엔티티: {}", serializedImagePath);
                } else {
                    logger.warn("직렬화된 이미지 엔티티를 찾을 수 없음: {}", serializedImagePath);
                }

                // DB에서 이미지 정보 삭제
                imageRepository.deleteById(image.getId());
                logger.info("삭제된 이미지 메타데이터: {}", image.getOriginalName());

            } catch (Exception e) {
                logger.error("이미지 삭제 중 오류 발생: {}", image.getOriginalName(), e);
            }
        });

        logger.info("미사용 이미지 정리 완료");
    }
}
