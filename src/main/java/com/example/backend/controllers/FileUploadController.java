package com.example.backend.controllers;

import com.example.backend.domain.user.User;
import com.example.backend.repos.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;


@RestController
@CrossOrigin
@RequestMapping("/upload")
public class FileUploadController {

    @Autowired
    UserRepo userRepo;

    String imgDirPath = "C:\\Users\\tim\\Desktop\\23.02.20_react_spring_app\\backend\\src\\main\\resources\\static\\img\\";

    @RequestMapping(method= RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("user_id") Long user_id) throws IOException {
        User user = userRepo.findById(user_id).get();

        String fileName = UUID.randomUUID().toString() + ".jpg";
        File convertFile = new File(imgDirPath + fileName);
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(file.getBytes());
        fout.close();

        user.setAvatar(fileName);
        userRepo.save(user);
        return new ResponseEntity<>("File is uploaded successfully", HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> deleteFile(@RequestBody User user) {
        System.out.println("sfafasfasfasf");
        User myUser = userRepo.findById(user.getId()).get();
        myUser.setAvatar(null);
        userRepo.save(myUser);
        return new ResponseEntity<>("succes", HttpStatus.OK);
    }



        @GetMapping
    public ResponseEntity<Object> getFile(@RequestParam("user_id") Long user_id) throws IOException {
        String fileName = imgDirPath + userRepo.findById(user_id).get().getAvatar();
        File file = new File(fileName);
            ResponseEntity<Object> responseEntity;
            if (file.exists()) {
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

                HttpHeaders headers = new HttpHeaders();

                responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(
                        MediaType.parseMediaType("image/jpeg")).body(resource);
            } else {
                responseEntity = new ResponseEntity<>(null, HttpStatus.OK);
            }
        return responseEntity;
    }

}