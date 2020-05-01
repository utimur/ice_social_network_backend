package com.example.backend.repos.user;


import com.example.backend.domain.user.Followers;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FollowersRepo extends CrudRepository<Followers, Long> {
    public List<Followers> findFollowersByUserId(Long userId);

    public Followers findByUserIdAndFollowerId(Long userId, Long followerId);

    public List<Followers> findFollowersByFollowerId(Long followerId);
}
