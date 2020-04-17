package com.example.backend.domain;


import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@JsonAutoDetect
@Table
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String img;
    String text;

    @Column(updatable = false)
    String creation;

    Long likes;
    Long reposts;

    Long user_id;

    public Post() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreation() {
        return creation;
    }

    public void setCreation(String creation) {
        this.creation = creation;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getReposts() {
        return reposts;
    }

    public void setReposts(Long reposts) {
        this.reposts = reposts;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
