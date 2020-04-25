package com.example.backend.repos.post;

import com.example.backend.domain.post.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepo extends CrudRepository<Post, Long> {
    public List<Post> findPostsByUserId(Long userId);
}
