package com.example.backend.repos;

import com.example.backend.domain.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepo extends CrudRepository<Post, Long> {
}
