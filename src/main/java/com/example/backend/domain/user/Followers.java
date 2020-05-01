package com.example.backend.domain.user;


import javax.persistence.*;

@Entity
@Table(name = "followers")
public class Followers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Long userId;
    Long followerId;

    public Followers() {
    }

    public Followers(Long userId, Long followerId) {
        this.userId = userId;
        this.followerId = followerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }
}
