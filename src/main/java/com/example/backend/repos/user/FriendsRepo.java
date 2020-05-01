package com.example.backend.repos.user;

import com.example.backend.domain.user.Friends;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FriendsRepo extends CrudRepository<Friends, Long> {
    public List<Friends> findFriendsByUserId(Long userId);

    public Friends findByUserIdAndFriendId(Long userId, Long friendId);
}
