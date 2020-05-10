package com.example.backend.controllers;


import com.example.backend.domain.dialog.Dialog;
import com.example.backend.domain.dialog.Message;
import com.example.backend.domain.user.User;
import com.example.backend.repos.dialog.DialogRepo;
import com.example.backend.repos.dialog.MessageRepo;
import com.example.backend.repos.user.UserRepo;
import com.example.backend.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/message")
public class MessageController {

    final MessageRepo messageRepo;
    final DialogRepo dialogRepo;
    final UserRepo userRepo;

    public MessageController(MessageRepo messageRepo, DialogRepo dialogRepo, UserRepo userRepo) {
        this.messageRepo = messageRepo;
        this.dialogRepo = dialogRepo;
        this.userRepo = userRepo;
    }


    @GetMapping
    public ResponseEntity<List<Message>> getMessages(@RequestParam(name = "id") Long dialogId) {
        List<Message> messages = messageRepo.findMessagesByDialogId(dialogId);
        for (Message mess : messages) {
            ImageService.setMessageImg(mess);
        }
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Message> addMessage(
                                        @RequestParam(value = "img", required = false) MultipartFile img,
                                        @RequestParam("user_id") Long userId,
                                        @RequestParam("dialog_id") Long dialogId,
                                        @RequestParam("text") String text) throws IOException {
        Dialog dialog = dialogRepo.findById(dialogId).get();

        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyy hh:mm");

        dialog.setLastMessage(text);
        dialog.setLastMessageDate(formatForDateNow.format(dateNow));

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        dialogRepo.save(dialog);

        Message message = new Message(dialogId,
                userRepo.findById(userId).get(),
                text,
                formatForDateNow.format(dateNow));

        if(img != null) {
            String fileName = UUID.randomUUID().toString() + ".jpg";
            File convertFile = new File(ImageService.imgDirPath + fileName);
            convertFile.createNewFile();
            FileOutputStream fout = new FileOutputStream(convertFile);
            fout.write(img.getBytes());
            fout.close();
            message.setImg(fileName);
            ImageService.setMessageImg(message);
        }
        return new ResponseEntity(messageRepo.save(message), HttpStatus.OK);
    }

}
