package com.example.backend.repos.user;


import com.example.backend.domain.user.Followers;
import org.springframework.data.repository.CrudRepository;

public interface FollowersRepo extends CrudRepository<Followers, Long> {

}
