package com.example.ProjectLibrary.repo;

import com.example.ProjectLibrary.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepo extends JpaRepository<Book, Long> {
    List<Book> findByTitle(String title);
}
