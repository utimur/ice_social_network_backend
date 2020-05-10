package com.example.backend.controllers;


import com.example.backend.domain.user.Followers;
import com.example.backend.domain.user.Friends;
import com.example.backend.domain.user.User;
import com.example.backend.repos.user.FollowersRepo;
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
    @Autowired
    FollowersRepo followersRepo;

    @PostMapping
    public ResponseEntity<User> addFriend(@RequestBody User user){
        User myUser = userRepo.findById(user.getId()).get();
        User friend = userRepo.findById(user.getFriendId()).get();

        if (friendsRepo.findByUserIdAndFriendId(myUser.getId(), friend.getId()) == null) {
            if(followersRepo.findByUserIdAndFollowerId(myUser.getId(),friend.getId())==null)
            {
                followersRepo.save(new Followers(myUser.getId(), friend.getId()));
                myUser.setFollowingCount(myUser.getFollowingCount()+1);
                friend.setFollowersCount(friend.getFollowersCount()+1);
            }
            if (followersRepo.findByUserIdAndFollowerId(myUser.getId(), friend.getId()) != null
                    && followersRepo.findByUserIdAndFollowerId(friend.getId(), myUser.getId()) != null) {
                friendsRepo.save(new Friends(myUser.getId(),friend.getId()));
                friendsRepo.save(new Friends(friend.getId(),myUser.getId()));
                followersRepo.delete(followersRepo.findByUserIdAndFollowerId(myUser.getId(), friend.getId()));
                followersRepo.delete(followersRepo.findByUserIdAndFollowerId(friend.getId(), myUser.getId()));
                friend.setIsFriend(true);
                myUser.setFriendsCount(myUser.getFriendsCount()+1);
                friend.setFriendsCount(friend.getFriendsCount()+1);
                myUser.setFollowersCount(myUser.getFollowersCount()-1);
                myUser.setFollowingCount(myUser.getFollowingCount()-1);
                friend.setFollowingCount(friend.getFollowingCount()-1);
                friend.setFollowersCount(friend.getFollowersCount()-1);
            }
        }
        userRepo.save(myUser);
        userRepo.save(friend);
        return new ResponseEntity(user, HttpStatus.OK);
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
        System.out.println("find");
        if (friendsRepo.findByUserIdAndFriendId(id, friendId) != null
                || followersRepo.findByUserIdAndFollowerId(id, friendId) != null) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteFriend(@RequestParam(name = "id") Long id,
                                                @RequestParam(name = "friend_id") Long friendId) {
        User myUser = userRepo.findById(id).get();
        User friend = userRepo.findById(friendId).get();

        if (friendsRepo.findByUserIdAndFriendId(id, friendId) != null) {
            friendsRepo.delete(friendsRepo.findByUserIdAndFriendId(id, friendId));
            friendsRepo.delete(friendsRepo.findByUserIdAndFriendId(friendId, id));
            followersRepo.save(new Followers(friendId, id));

            myUser.setFriendsCount(myUser.getFriendsCount()-1);
            friend.setFriendsCount(friend.getFriendsCount()-1);
            myUser.setFollowersCount(myUser.getFollowersCount()+1);
            friend.setFollowingCount(friend.getFollowingCount()+1);
        } else {
            followersRepo.delete(followersRepo.findByUserIdAndFollowerId(id,friendId));
            myUser.setFollowingCount(myUser.getFollowingCount()-1);
            friend.setFollowersCount(friend.getFollowersCount()-1);
        }
        userRepo.save(myUser);
        userRepo.save(friend);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/following")
    public ResponseEntity<List<User>> getFollowing(@RequestParam(name = "id") Long id) {
        List<User> followers = new ArrayList<>();

        List<Followers> followersIds = followersRepo.findFollowersByUserId(id);
        for (Followers follower:followersIds) {
            String avatar = imgDirPath + userRepo.findById(follower.getFollowerId()).get().getAvatar();
            User user = userRepo.findById(follower.getFollowerId()).get();
            File avatarFile = new File(avatar);
            if (avatarFile.exists()) {
                user.setAvatarStr(ImageService.encodeFileToBase64Binary(avatarFile));
            }
            followers.add(user);
        }
        return new ResponseEntity<>(followers, HttpStatus.OK);
    }

    @GetMapping("/followers")
    public ResponseEntity<List<User>> getFollowers(@RequestParam(name = "id") Long id) {
        List<User> followers = new ArrayList<>();

        List<Followers> followersIds = followersRepo.findFollowersByFollowerId(id);
        for (Followers follower:followersIds) {
            String avatar = imgDirPath + userRepo.findById(follower.getUserId()).get().getAvatar();
            User user = userRepo.findById(follower.getUserId()).get();
            File avatarFile = new File(avatar);
            if (avatarFile.exists()) {
                user.setAvatarStr(ImageService.encodeFileToBase64Binary(avatarFile));
            }
            followers.add(user);
        }
        return new ResponseEntity<>(followers, HttpStatus.OK);
    }

}
