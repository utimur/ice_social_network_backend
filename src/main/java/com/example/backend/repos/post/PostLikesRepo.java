package com.example.backend.repos.post;

import com.example.backend.domain.post.PostLikes;
import org.springframework.data.repository.CrudRepository;

public interface PostLikesRepo extends CrudRepository<PostLikes, Long> {
    public PostLikes findByPostIdAndUserId(Long postId, Long userId);
}
