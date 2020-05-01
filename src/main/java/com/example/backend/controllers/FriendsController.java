package com.example.backend.controllers;


import com.example.backend.domain.user.Friends;
import com.example.backend.domain.user.User;
import com.example.backend.repos.user.FriendsRepo;
import com.example.backend.repos.user.UserRepo;
import com.example.backend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/friends")
@CrossOrigin
public class FriendsController {

    String imgDirPath = "C:\\Users\\tim\\Desktop\\23.02.20_react_spring_app\\backend\\src\\main\\resources\\static\\img\\";


    @Autowired
    UserRepo userRepo;
    @Autowired
    FriendsRepo friendsRepo;

    @PostMapping
    public ResponseEntity<User> addFriend(@RequestBody User user){
        User myUser = userRepo.findById(user.getId()).get();
        User friend = userRepo.findById(user.getFriendId()).get();

        if (friendsRepo.findByUserIdAndFriendId(myUser.getId(), friend.getId()) == null) {
            friendsRepo.save(new Friends(myUser.getId(),friend.getId()));
        }

        return new ResponseEntity(friend, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getFriend(@RequestParam Long id) {

        List<User> friends = new ArrayList<>();
        if (id == 0) {
            friends = userRepo.findAll();
            for (User user : friends) {
                String avatar = imgDirPath + user.getAvatar();
                File avatarFile = new File(avatar);
                if (avatarFile.exists()) {
                    user.setAvatarStr(ImageService.encodeFileToBase64Binary(avatarFile));
                }
            }
            return new ResponseEntity<>(friends, HttpStatus.OK);
        }
        List<Friends> friendIds = friendsRepo.findFriendsByUserId(id);
        for (Friends friend:friendIds) {
            String avatar = imgDirPath + userRepo.findById(friend.getFriendId()).get().getAvatar();
            User user = userRepo.findById(friend.getFriendId()).get();
            File avatarFile = new File(avatar);
            if (avatarFile.exists()) {
                user.setAvatarStr(ImageService.encodeFileToBase64Binary(avatarFile));
            }
            friends.add(user);
        }
        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<Boolean> findFriend(@RequestParam(name = "id") Long id,
                                              @RequestParam(name = "friend_id") Long friendId) {
        if (friendsRepo.findByUserIdAndFriendId(id, friendId) != null) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteFriend(@RequestParam(name = "id") Long id,
                                                @RequestParam(name = "friend_id") Long friendId) {
        friendsRepo.delete(friendsRepo.findByUserIdAndFriendId(id, friendId));
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
