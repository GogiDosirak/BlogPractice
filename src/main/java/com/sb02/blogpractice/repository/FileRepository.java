package com.sb02.blogpractice.repository;

import java.util.List;
import java.util.UUID;

public interface FileRepository<T> {
    void saveToFile(T t);
    List<T> loadAllFromFile();
    void deleteFileById(UUID id);

}
