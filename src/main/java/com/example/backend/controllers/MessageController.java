package com.example.backend.controllers;

import com.example.backend.domain.Message;
import com.example.backend.domain.MessageWithAuhtor;
import com.example.backend.domain.User;
import com.example.backend.repos.MessageRepo;
import com.example.backend.repos.UserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("dialogs")
public class MessageController {

    @Autowired
    MessageRepo messageRepo;
    @Autowired
    UserRepo userRepo;

    @GetMapping("/chat")
    public List getMessages(@RequestParam Long id1,
                            @RequestParam Long id2) throws JsonProcessingException {

        List<String> messages = new ArrayList<>();


        return messages;
    }

    @PostMapping("/chat")
    public String addMessage(@RequestBody Message message) throws JsonProcessingException {
        message.setAuthorId(message.getAuthorId());
        User user = userRepo.findById(message.getAuthorId()).get();
        MessageWithAuhtor messageWithAuhtor = new MessageWithAuhtor(message, user);
        messageRepo.save(message);
        return messageWithAuhtor.toJson();
    }

}
