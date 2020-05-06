package com.example.backend.service;

import com.example.backend.domain.dialog.Message;
import com.example.backend.domain.user.User;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImageService {

    public static String imgDirPath = "C:\\Users\\tim\\Desktop\\23.02.20_react_spring_app\\backend\\src\\main\\resources\\static\\img\\";


    public static String encodeFileToBase64Binary(File file) {
        String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return encodedfile;
    }

    public static void setAvatarStr(User user) {
        String avatar = imgDirPath + user.getAvatar();
        File avatarFile = new File(avatar);
        if (avatarFile.exists()) {
            user.setAvatarStr(ImageService.encodeFileToBase64Binary(avatarFile));
        }
    }

    public static void setMessageImg(Message message) {
        String img = imgDirPath + message.getImg();
        File imgFile = new File(img);
        if (imgFile.exists()) {
            message.setImgStr(ImageService.encodeFileToBase64Binary(imgFile));
        }
    }
}
