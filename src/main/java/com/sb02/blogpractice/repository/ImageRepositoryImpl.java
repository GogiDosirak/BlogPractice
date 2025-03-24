package com.sb02.blogpractice.repository;

import com.sb02.blogpractice.entity.Image;
import com.sb02.blogpractice.entity.User;
import com.sb02.blogpractice.util.SerializationUtil;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ImageRepositoryImpl implements ImageRepository, FileRepository<Image> {
    private final Path directory;
    private final Map<UUID, Image> imageMap;

    public ImageRepositoryImpl() {
        this.directory = Paths.get(System.getProperty("user.dir"), "data", "image");
        imageMap = new ConcurrentHashMap<>();
        SerializationUtil.init(directory);
    }

    @Override
    public Image save(Image image) {
        saveToFile(image);
        imageMap.put(image.getId(), image);
        return image;
    }

    @Override
    public void saveToFile(Image image) {
        Path filePath = directory.resolve(image.getId() + ".ser");
        SerializationUtil.init(directory);
        SerializationUtil.serialization(filePath, image);
    }

    @Override
    public List<Image> loadAllFromFile() {
        return SerializationUtil.reverseSerialization(directory);
    }

    @Override
    public void deleteFileById(UUID id) {
        Path filePath = directory.resolve(id + ".ser");
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.out.println("이미지 파일 삭제 예외 발생 : " + e.getMessage());
        }
    }

    private void loadCacheFromFile() {
        List<Image> images = loadAllFromFile();
        for (Image image : images) {
            imageMap.put(image.getId(), image);
        }
    }
}
