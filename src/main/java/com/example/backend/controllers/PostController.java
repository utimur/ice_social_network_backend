package com.example.backend.controllers;

import com.example.backend.domain.post.Post;
import com.example.backend.domain.user.User;
import com.example.backend.domain.post.PostComments;
import com.example.backend.domain.post.PostLikes;
import com.example.backend.repos.post.PostCommentsRepo;
import com.example.backend.repos.post.PostRepo;
import com.example.backend.repos.user.UserRepo;
import com.example.backend.repos.post.PostLikesRepo;
import com.example.backend.service.ImageService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostRepo postRepo;
    @Autowired
    PostLikesRepo postLikesRepo;
    @Autowired
    PostCommentsRepo postCommentsRepo;
    @Autowired
    UserRepo userRepo;

    String imgDirPath = "C:\\Users\\tim\\Desktop\\23.02.20_react_spring_app\\backend\\src\\main\\resources\\static\\img\\";

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> addPost(@RequestParam(value = "img", required = false) MultipartFile img,
                                          @RequestParam("user_id") Long user_id,
                                          @RequestParam("creator_id") Long creator_id,
                                          @RequestParam("text") String text) throws IOException {
        Post post = new Post();

        if(img != null) {
            String fileName = UUID.randomUUID().toString() + ".jpg";
            File convertFile = new File(imgDirPath + fileName);
            convertFile.createNewFile();
            FileOutputStream fout = new FileOutputStream(convertFile);
            fout.write(img.getBytes());
            fout.close();
            post.setImg(fileName);
        }
        User creator = userRepo.findById(creator_id).get();
        post.setUserId(user_id);
        post.setCreatorId(creator_id);
        post.setName(creator.getName());
        post.setSurname(creator.getSurname());
        post.setText(text);
        post.setLikes(new Long(0));
        post.setReposts(new Long(0));
        post.setComments(new Long(0));

        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyy hh:mm");

        post.setCreation(formatForDateNow.format(dateNow));

        setPostFile(post);


        postRepo.save(post);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }


    @PutMapping
    public ResponseEntity<Post> updatePost(@RequestBody Post post) {

        Post dbPost = postRepo.findById(post.getId()).get();

        if (post.getLikes() != null){
            PostLikes postLikes = postLikesRepo.findByPostIdAndUserId(post.getId(), post.getLikerId());
            Long like;
            if (postLikes == null) {
                like = dbPost.getLikes() + 1;
                dbPost.setLiked(true);
                dbPost.setLikes(like);
                postLikesRepo.save(new PostLikes(post.getLikerId(), post.getId()));
            } else {
                like = dbPost.getLikes() - 1;
                dbPost.setLiked(false);
                dbPost.setLikes(like);
                postLikesRepo.delete(postLikes);
            }
        }
        if (post.getComments() != null){
            Long comments = dbPost.getComments()+new Long(1);
            dbPost.setComments(comments);
        }
        if (post.getReposts() != null){
            Long reposts = dbPost.getReposts()+new Long(1);
            dbPost.setReposts(reposts);
        }
        postRepo.save(dbPost);
        return new ResponseEntity<>(dbPost, HttpStatus.OK);
    }

    @GetMapping
    public List<Post> getPosts(@RequestParam("user_id") Long user_id, @RequestParam("my_id") Long my_id) throws FileNotFoundException {

        List<Post> postList = postRepo.findPostsByUserId(user_id);
        for (Post post: postList) {
            PostLikes postLikes = postLikesRepo.findByPostIdAndUserId(post.getId(),my_id);
            if(postLikes != null){
                post.setLiked(true);
            } else {
                post.setLiked(false);
            }
            setPostFile(post);
        }
        Collections.reverse(postList);
        return postList;
    }

    @DeleteMapping
    public ResponseEntity<Object> deletePost(@RequestParam("post_id") Long post_id) {
        postRepo.delete(postRepo.findById(post_id).get());
        return new ResponseEntity("succes", HttpStatus.OK);
    }

    @GetMapping("/comments")
    public ResponseEntity<List<PostComments>> getComments(@RequestParam("post_id") Long post_id) {
        List<PostComments> comments = postCommentsRepo.findPostCommentsByPostId(post_id);
        for (PostComments comment: comments) {
            setComment(comment);
        }
        return new ResponseEntity(comments, HttpStatus.OK);
    }

    @PostMapping("/comments")
    public ResponseEntity<PostComments> addComment(@RequestBody PostComments postComment) {
        PostComments comment = postCommentsRepo.save(postComment);
        Post post = postRepo.findById(comment.getPostId()).get();
        post.setComments(post.getComments() + 1);
        setComment(comment);
        postRepo.save(post);
        comment.setCommentsCount(post.getComments());
        return new ResponseEntity(comment, HttpStatus.OK);
    }

    @DeleteMapping("/comments")
    public ResponseEntity deleteComment(@RequestParam Long id) {
        PostComments postComments = postCommentsRepo.findById(id).get();
        Post post = postRepo.findById(postComments.getPostId()).get();
        postCommentsRepo.deleteById(id);
        post.setComments(post.getComments()-1);
        postRepo.save(post);
        return new ResponseEntity(HttpStatus.OK);
    }


    private void setComment(PostComments comment) {
        String avatar = imgDirPath + userRepo.findById(comment.getUserId()).get().getAvatar();

        File avatarFile = new File(avatar);
        if (avatarFile.exists()) {
            comment.setAvatar(ImageService.encodeFileToBase64Binary(avatarFile));
        }
        User user = userRepo.findById(comment.getUserId()).get();
        comment.setUsername(user.getSurname() + " " + user.getName());
    }

    private void setPostFile(Post post) {
        String avatar = imgDirPath + userRepo.findById(post.getCreatorId()).get().getAvatar();
        File avatarFile = new File(avatar);
        if (avatarFile.exists()) {
            post.setAvatar(ImageService.encodeFileToBase64Binary(avatarFile));
        }

        String imgPath = imgDirPath + post.getImg();
        File postImg = new File(imgPath);
        if (postImg.exists()) {
            post.setPostImg(ImageService.encodeFileToBase64Binary(postImg));
        }
    }

}
