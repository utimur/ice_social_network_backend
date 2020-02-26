package com.example.backend.controllers;


import com.example.backend.domain.User;
import com.example.backend.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @PostMapping("registration")
    public User registration(@RequestBody User user){
        return userRepo.save(user);
    }


}
