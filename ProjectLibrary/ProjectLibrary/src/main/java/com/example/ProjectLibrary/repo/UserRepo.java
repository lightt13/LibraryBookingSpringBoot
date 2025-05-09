package com.example.ProjectLibrary.repo;

import com.example.ProjectLibrary.model.Book;
import com.example.ProjectLibrary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    List<User> findByEmail(String email);
}
