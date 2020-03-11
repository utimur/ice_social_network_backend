package com.example.backend.controllers;

import com.example.backend.domain.Message;
import com.example.backend.domain.MessageWithAuhtor;
import com.example.backend.domain.User;
import com.example.backend.repos.MessageRepo;
import com.example.backend.repos.UserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("message")
public class MessageController {

    @Autowired
    MessageRepo messageRepo;
    @Autowired
    UserRepo userRepo;

    @GetMapping
    public List getMessages() throws JsonProcessingException {
        System.out.println("getMessages");
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> messages = new ArrayList<>();
        for (Message msg: messageRepo.findAll()) {
            User author = userRepo.findById(msg.getAuthorId()).get();
            MessageWithAuhtor messageWithAuhtor = new MessageWithAuhtor(msg,author);
            messages.add(messageWithAuhtor.toJson());
        }
        return messages;
    }

    @GetMapping("{id}")
    public String getOne(@PathVariable Long id) throws JsonProcessingException {
        Message message = messageRepo.findById(id).get();
        User user = userRepo.findById(message.getAuthorId()).get();
        MessageWithAuhtor messageWithAuhtor = new MessageWithAuhtor(message, user);
        return messageWithAuhtor.toJson();
    }

    @PostMapping
    public String addMessage(@RequestBody Message message) throws JsonProcessingException {
        message.setAuthorId(message.getAuthorId());
        User user = userRepo.findById(message.getAuthorId()).get();
        MessageWithAuhtor messageWithAuhtor = new MessageWithAuhtor(message, user);
        messageRepo.save(message);
        return messageWithAuhtor.toJson();
    }

}
