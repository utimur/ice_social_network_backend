package com.example.backend.controllers;

import com.example.backend.repos.MessageRepo;
import com.example.backend.repos.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("dialogs")
public class MessageController {

    @Autowired
    MessageRepo messageRepo;
    @Autowired
    UserRepo userRepo;


}
