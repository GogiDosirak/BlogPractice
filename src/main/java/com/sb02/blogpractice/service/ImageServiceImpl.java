package com.sb02.blogpractice.service;

import com.sb02.blogpractice.entity.Image;
import com.sb02.blogpractice.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService{
    private final ImageRepository imageRepository;
    String fileDir;
    Path savePath;

    public ImageServiceImpl(ImageRepository imageRepository,  @Value("${BlogPractice.service.file-directory}") String fileDir) {
        this.imageRepository = imageRepository;
        this.fileDir = fileDir;
        this.savePath = Paths.get(System.getProperty("user.dir"), fileDir);
    }


    @Override
    public Image upload(MultipartFile multipartFile) throws IOException {
        File dest = saveImageFile(multipartFile);
        Image image = fileToImageEntity(multipartFile, dest);
        imageRepository.save(image);
        return image;
    }

    private File saveImageFile (MultipartFile multipartFile) throws IOException {
        String originalName = multipartFile.getOriginalFilename();
        String uuidName = UUID.randomUUID() + "_" + originalName;;

        if (!Files.exists(savePath)) {
            Files.createDirectories(savePath);
        }

        File dest = new File(String.valueOf(savePath), uuidName);
        multipartFile.transferTo(dest);
        return dest;
    }

    private Image fileToImageEntity(MultipartFile multipartFile, File dest) {
        String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());

        Image image = Image.builder()
                .size(multipartFile.getSize())
                .originalName(multipartFile.getOriginalFilename())
                .path(dest.getAbsolutePath())
                .extension(extension)
                .build();

        return image;
    }
}
