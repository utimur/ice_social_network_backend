package com.example.backend.controllers;


import com.example.backend.domain.User;
import com.example.backend.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepo userRepo;

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
        System.out.println("1");
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        if (userDb != null) {
            System.out.println("2");
            if (user.getPassword().equals(userDb.getPassword())) {
                System.out.println("3");
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


}
