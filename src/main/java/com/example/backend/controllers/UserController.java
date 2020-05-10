package com.example.backend.controllers;


import com.example.backend.domain.user.User;
import com.example.backend.repos.user.UserRepo;
import com.example.backend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@CrossOrigin
public class UserController {
    String imgDirPath = "C:\\Users\\tim\\Desktop\\23.02.20_react_spring_app\\backend\\src\\main\\resources\\static\\img\\";

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/users")
    public Iterable<User> getUsers() {
        return userRepo.findAll();
    }

    @PostMapping("/registration")
    public ResponseEntity registration(@RequestBody User user){
        User userDb = userRepo.findByUsername(user.getUsername());
        if(userDb == null){
            if (user.getUsername().length() < 2 || user.getPassword().length() < 2) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            userRepo.save(user);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        User userDb = userRepo.findByUsername(user.getUsername());
        if (userDb != null) {
            if (user.getPassword().equals(userDb.getPassword())) {
                String avatar = imgDirPath + userDb.getAvatar();
                File avatarFile = new File(avatar);
                if (avatarFile.exists()) {
                    userDb.setAvatarStr(ImageService.encodeFileToBase64Binary(avatarFile));
                }
                return new ResponseEntity(userDb, HttpStatus.OK);
            }
        }
        return new ResponseEntity(null,HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<User> getProfile(@PathVariable Long id) {
        User user = userRepo.findById(id).get();
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        String avatar = imgDirPath + user.getAvatar();
        File avatarFile = new File(avatar);
        if (avatarFile.exists()) {
            user.setAvatarStr(ImageService.encodeFileToBase64Binary(avatarFile));
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/profile")
    public ResponseEntity<User> updateProfile(@RequestBody User user) {
        User dbUser = userRepo.findById(user.getId()).get();
        dbUser.setStatus(user.getStatus());
        ImageService.setAvatarStr(dbUser);
        userRepo.save(dbUser);
        return new ResponseEntity<>(dbUser, HttpStatus.OK);
    }

}
