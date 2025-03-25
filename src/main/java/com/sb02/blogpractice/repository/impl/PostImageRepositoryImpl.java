package com.sb02.blogpractice.repository.impl;

import com.sb02.blogpractice.entity.Post;
import com.sb02.blogpractice.entity.PostImage;
import com.sb02.blogpractice.repository.FileRepository;
import com.sb02.blogpractice.repository.PostImageRepository;
import com.sb02.blogpractice.util.SerializationUtil;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PostImageRepositoryImpl implements PostImageRepository, FileRepository<PostImage> {
    private final Path directory;
    private final Map<UUID, PostImage> postImageMap;

    public PostImageRepositoryImpl() {
        this.directory = Paths.get(System.getProperty("user.dir"), "data", "postImage");
        this.postImageMap = new ConcurrentHashMap<>();
        loadCacheFromFile();
    }

    @Override
    public PostImage save(PostImage post) {
        saveToFile(post);
        postImageMap.put(post.getId(), post);
        return post;
    }

    @Override
    public UUID deleteById(UUID id) {
        deleteFileById(id);
        postImageMap.remove(id);
        return id;
    }

    @Override
    public void saveToFile(PostImage postImage) {
        Path filePath = directory.resolve(postImage.getId() + ".ser");
        SerializationUtil.init(directory);
        SerializationUtil.serialization(filePath, postImage);
    }

    @Override
    public List<PostImage> loadAllFromFile() {
        return SerializationUtil.reverseSerialization(directory);
    }

    @Override
    public void deleteFileById(UUID id) {
        Path filePath = directory.resolve(id + ".ser");
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.out.println("게시글이미지 파일 삭제 예외 발생 : " + e.getMessage());
        }
    }

    private void loadCacheFromFile() {
        List<PostImage> postImages = loadAllFromFile();
        for(PostImage postImage : postImages) {
            postImageMap.put(postImage.getId(), postImage);
        }
    }
}
