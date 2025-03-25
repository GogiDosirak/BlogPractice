package com.sb02.blogpractice.repository;

import com.sb02.blogpractice.entity.User;
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
public class UserRepositoryImpl implements UserRepository, FileRepository<User>{
    private final Path directory;
    private final Map<String, User> userMap;

    public UserRepositoryImpl() {
        this.directory = Paths.get(System.getProperty("user.dir"), "data", "user");
        SerializationUtil.init(directory);
        this.userMap = new ConcurrentHashMap<>();
        loadCacheFromFile();
    }

    @Override
    public User save(User user) {
        saveToFile(user);
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(userMap.get(id));
    }

    @Override
    public void saveToFile(User user) {
        Path filePath = directory.resolve(user.getId() + ".ser");
        SerializationUtil.init(directory);
        SerializationUtil.serialization(filePath, user);
    }

    @Override
    public List<User> loadAllFromFile() {
        return SerializationUtil.reverseSerialization(directory);
    }

    @Override
    public void deleteFileById(UUID id) {
        Path filePath = directory.resolve(id + ".ser");
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.out.println("유저 파일 삭제 예외 발생 : " + e.getMessage());
        }
    }

    private void loadCacheFromFile() {
        List<User> users = loadAllFromFile();
        for (User user : users) {
            userMap.put(user.getId(), user);
        }
    }
}
