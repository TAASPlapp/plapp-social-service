package com.plapp.socialservice.controllers;


import com.plapp.entities.social.Comment;
import com.plapp.entities.social.MediaContentType;
import com.plapp.entities.social.UserDetails;
import com.plapp.entities.utils.ApiResponse;
import com.plapp.socialservice.repositories.CommentRepository;
import com.plapp.socialservice.repositories.UserDetailsRepository;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/social")
public class SocialController {

    private CommentRepository commentRepository;
    private UserDetailsRepository userDetailsRepository;


    @Autowired
    public SocialController(@RequestBody CommentRepository commentRepository,
                            UserDetailsRepository userDetailsRepository) {
        this.commentRepository = commentRepository;
        this.userDetailsRepository = userDetailsRepository;
    }


    @CrossOrigin
    @GetMapping("/user")
    UserDetails getUserDetails(@RequestBody long userId) throws Exception {
        return userDetailsRepository.findByUserId(userId);
    }

    @CrossOrigin
    @PostMapping("/user/modify")
    ApiResponse setUserDetails(@RequestBody UserDetails userDetails) throws Exception {
        UserDetails user = userDetailsRepository.findByUserId(userDetails.getUserId());
        user.setBio(userDetails.getBio());
        user.setBirthdate(userDetails.getBirthdate());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setProfilePicture(userDetails.getProfilePicture());
        user.setUsername(userDetails.getUsername());
        try {
            userDetailsRepository.save(user);
        } catch (HibernateException e) {
            return new ApiResponse(false, e.getMessage());
        }
        return new ApiResponse(true, "user datails updated");


    }

    @CrossOrigin
    @GetMapping("/comments")
    List<Comment> getComments(@RequestBody MediaContentType type, long itemId) throws Exception {
        return commentRepository.findByMediaContentTypeAndAndItemId(type, itemId);
    }

    @CrossOrigin
    @PostMapping("/comments/add")
    ApiResponse addComment(@RequestBody Comment comment) throws Exception {
        try {
            commentRepository.save(comment);
        } catch (HibernateException e) {
            return new ApiResponse(false, e.getMessage());
        }
        return new ApiResponse();
    }

//    @CrossOrigin
//    @GetMapping
//    ApiResponse like(MediaContentType type, long itemId) throws Exception {
//        return null
//    }
//
//    @CrossOrigin
//    @GetMapping
//    ApiResponse unlike(MediaContentType type, long itemId) throws Exception {
//        return null
//    }
//
//    @CrossOrigin
//    @GetMapping
//    List<UserDetails> getLikes(MediaContentType type, long itemId) throws Exception {
//        return null
//    }


}
