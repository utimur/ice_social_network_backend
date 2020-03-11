package com.example.backend.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonAutoDetect
public class MessageWithAuhtor {
    Message message;
    User user;

    public MessageWithAuhtor(Message message, User user) {
        this.message = message;
        this.user = user;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(message);
        String json2= objectMapper.writeValueAsString(user);
        return "{\"message\":" + json +", \"user\":" +json2 + "}";
    }
}
