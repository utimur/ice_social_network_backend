package com.example.backend.controllers;


import com.example.backend.domain.dialog.Dialog;
import com.example.backend.domain.user.User;
import com.example.backend.repos.dialog.DialogRepo;
import com.example.backend.repos.dialog.MessageRepo;
import com.example.backend.repos.user.UserRepo;
import com.example.backend.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/dialog")
public class DialogController {

    final private DialogRepo dialogRepo;
    final private MessageRepo messageRepo;
    final private UserRepo userRepo;

    String imgDirPath = "C:\\Users\\tim\\Desktop\\23.02.20_react_spring_app\\backend\\src\\main\\resources\\static\\img\\";


    public DialogController(DialogRepo dialogRepo, MessageRepo messageRepo, UserRepo userRepo) {
        this.dialogRepo = dialogRepo;
        this.messageRepo = messageRepo;
        this.userRepo = userRepo;
    }

    @PostMapping
    public ResponseEntity<List<Dialog>> addDialog(@RequestBody Dialog dialog) {
        User user = userRepo.findById(dialog.getUser().getId()).get();
        User friend = userRepo.findById(dialog.getFriend().getId()).get();

        ImageService.setAvatarStr(user);
        ImageService.setAvatarStr(friend);

        List<Dialog> dialogs;
        if (dialogRepo.findByUserAndFriend(user, friend) == null &&
                dialogRepo.findByUserAndFriend(friend, user) == null) {
            dialogs = Arrays.asList(
                    dialogRepo.save(new Dialog(user, friend)),
                    dialogRepo.save(new Dialog(friend, user))
                    );
            return new ResponseEntity<>( dialogs ,HttpStatus.OK);
        }
        if (dialogRepo.findByUserAndFriend(user, friend) == null &&
                dialogRepo.findByUserAndFriend(friend, user) != null){
            dialogs = Arrays.asList(
                    dialogRepo.save(new Dialog(user, friend)),
                    dialogRepo.findByUserAndFriend(friend, user)
                    );
            return new ResponseEntity<>( dialogs ,HttpStatus.OK);

        }
        if (dialogRepo.findByUserAndFriend(user, friend) != null &&
                dialogRepo.findByUserAndFriend(friend, user) == null){
            dialogs = Arrays.asList(
                    dialogRepo.findByUserAndFriend(user, friend),
                    dialogRepo.save(new Dialog(friend, user))
            );
            return new ResponseEntity<>( dialogs,HttpStatus.OK);
        }
        return new ResponseEntity<>(null ,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Dialog>> getDialog(@RequestParam(name = "id") Long id) {

        List<Dialog> dialogs = dialogRepo.findByUserIdOrderByDialogIndexDesc(id);
        for (Dialog dialog : dialogs) {
            ImageService.setAvatarStr(dialog.getFriend());
            ImageService.setAvatarStr(dialog.getUser());
        }

        return new ResponseEntity(dialogs, HttpStatus.OK);
    }

    @GetMapping("/one")
    public ResponseEntity<List<Dialog>> getOneDialog(@RequestParam(name = "user_id") Long userId,
                                                    @RequestParam(name = "friend_id") Long friendId) {

        Dialog myDialog = dialogRepo.findByUserIdAndFriendId(userId, friendId);
        Dialog friendDialog = dialogRepo.findByUserIdAndFriendId(friendId, userId);
        ImageService.setAvatarStr(myDialog.getFriend());
        ImageService.setAvatarStr(myDialog.getUser());
        List<Dialog> dialogs = Arrays.asList(myDialog, friendDialog);
        if (dialogRepo.findByUserIdAndFriendId(userId, friendId) != null) {
            return new ResponseEntity(dialogs, HttpStatus.OK);
        } else {
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
    }
}
