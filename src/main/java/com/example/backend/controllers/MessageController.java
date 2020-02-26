package com.example.backend.controllers;

import com.example.backend.domain.Message;
import com.example.backend.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("message")
public class MessageController {

    @Autowired
    MessageRepo messageRepo;

    @GetMapping
    public Iterable<Message> getMessages() {
        return messageRepo.findAll();
    }

    @GetMapping("{id}")
    public Message getOne(@PathVariable Long id) {
        return messageRepo.findById(id).get();
    }

    @PostMapping
    public Message addMessage(@RequestBody Message message) {
        return messageRepo.save(message);
    }

}
