package com.example.backend.repos.user;

import com.example.backend.domain.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends CrudRepository<User, Long> {
    public User findByUsername(String username);
    public List<User> findAll();
}
