package com.example.backend.repos.post;

import com.example.backend.domain.post.PostComments;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostCommentsRepo extends CrudRepository<PostComments, Long> {
    public List<PostComments> findPostCommentsByPostId(Long postId);
}
