package com.sb02.blogpractice.repository.impl;

import com.sb02.blogpractice.entity.Post;
import com.sb02.blogpractice.repository.FileRepository;
import com.sb02.blogpractice.repository.PostRepository;
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
public class PostRepositoryImpl implements PostRepository, FileRepository<Post> {
    private final Path directory;
    private final Map<UUID, Post> postMap;

    public PostRepositoryImpl() {
        this.directory = Paths.get(System.getProperty("user.dir"), "data", "post");
        this.postMap = new ConcurrentHashMap<>();
        SerializationUtil.init(directory);
        loadCacheFromFile();
    }

    @Override
    public Post save(Post post) {
        saveToFile(post);
        postMap.put(post.getId(), post);
        return post;
    }

    @Override
    public Optional<Post> findById(UUID id) {
        return Optional.ofNullable(postMap.get(id));
    }

    @Override
    public List<Post> findAll() {
        return postMap.values().stream().toList();
    }

    @Override
    public UUID deleteById(UUID id) {
        deleteFileById(id);
        postMap.remove(id);
        return id;
    }

    @Override
    public void saveToFile(Post post) {
        Path filePath = directory.resolve(post.getId() + ".ser");
        SerializationUtil.init(directory);
        SerializationUtil.serialization(filePath, post);

    }

    @Override
    public List<Post> loadAllFromFile() {
        return SerializationUtil.reverseSerialization(directory);
    }

    @Override
    public void deleteFileById(UUID id) {
        Path filePath = directory.resolve(id + ".ser");
        try {
            Files.deleteIfExists(filePath);
        } catch(IOException e) {
            System.out.println("게시물 파일 삭제 예외 발생 : " + e.getMessage());
        }
    }

    private void loadCacheFromFile() {
        List<Post> posts = loadAllFromFile();
        for(Post post : posts) {
            postMap.put(post.getId(), post);
        }
    }
}
