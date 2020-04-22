package com.example.backend.controllers;


import com.example.backend.domain.User;
import com.example.backend.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class UserController {

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
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/profile")
    public ResponseEntity<User> updateProfile(@RequestBody User user) {
        System.out.println(user.getEmail());
        System.out.println(user.getId());
        System.out.println(user.getUsername());
        userRepo.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
