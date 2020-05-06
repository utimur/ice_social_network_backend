package com.example.backend.domain.dialog;


import com.example.backend.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Dialog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @JsonIgnore
    Long dialogIndex;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    User friend;
    String lastMessage;
    String lastMessageDate;

    public Dialog(User user, User friend) {
        this.user = user;
        this.friend = friend;
    }

    public Long getDialogIndex() {
        return dialogIndex;
    }

    public void setDialogIndex(Long dialogIndex) {
        this.dialogIndex = dialogIndex;
    }

    public Dialog() {
    }

    public String getLastMessageDate() {
        return lastMessageDate;
    }

    public void setLastMessageDate(String lastMessageDate) {
        this.lastMessageDate = lastMessageDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriend() {
        return friend;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
