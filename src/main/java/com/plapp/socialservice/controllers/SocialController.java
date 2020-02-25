package com.plapp.socialservice.controllers;


import com.plapp.entities.exceptions.ActorNotFoundException;
import com.plapp.entities.social.Comment;
import com.plapp.entities.social.Like;
import com.plapp.entities.social.MediaContentType;
import com.plapp.entities.social.UserDetails;
import com.plapp.entities.utils.ApiResponse;
import com.plapp.socialservice.repositories.CommentRepository;
import com.plapp.socialservice.repositories.LikeRepository;
import com.plapp.socialservice.repositories.UserDetailsRepository;
import com.plapp.socialservice.service.CommentService;
import com.plapp.socialservice.service.LikeService;
import com.plapp.socialservice.service.UserDetailsService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/social")
public class SocialController {

    private CommentService commentService;
    private LikeService likeService;
    private UserDetailsService userDetailsService;


    @Autowired
    public SocialController(CommentService commentService,
                            LikeService likeService,
                            UserDetailsService userDetailsService) {
        this.commentService = commentService;
        this.likeService = likeService;
        this.userDetailsService = userDetailsService;
    }


    @CrossOrigin
    @GetMapping("/user/{userId}")
    public UserDetails getUserDetails(@PathVariable(value = "userId") long userId) {
        return userDetailsService.findByUserId(userId);
    }

    @CrossOrigin
    @PostMapping("/user/add")
    public ApiResponse addUserDetails(@RequestBody UserDetails user) {
        try {
            userDetailsService.addUser(user);
        } catch (HibernateException e) {
            return new ApiResponse(false, e.getMessage());
        }
        return new ApiResponse(true, "user datails updated");
    }

    @CrossOrigin
    @PostMapping("/user/modify")
    public ApiResponse setUserDetails(@RequestBody UserDetails userDetails) {
        try {
            userDetailsService.modifyUserDetail(userDetails);
        } catch (HibernateException e) {
            return new ApiResponse(false, e.getMessage());
        }
        return new ApiResponse(true, "user datails updated");
    }

    @CrossOrigin
    @GetMapping("/comments")
    public List<Comment> getComments(@RequestBody MediaContentType type, @RequestParam long itemId) {
        return commentService.findByMediaContentTypeAndAndItemId(type, itemId);
    }

    @CrossOrigin
    @PostMapping("/comment/add")
    public ApiResponse addComment(@RequestBody Comment comment) {
        try {
            commentService.addComment(comment);
        } catch (HibernateException e) {
            return new ApiResponse(false, e.getMessage());
        }
        return new ApiResponse();
    }

    @CrossOrigin
    @PostMapping("/like/add")
    public ApiResponse addlike(@RequestParam Like like) {
        if (likeService.findByMediaContentTypeAndAndItemId(like.getMediaContentType(), like.getItemId()).isEmpty()) {
            try {
                likeService.addLike(like);
            } catch (HibernateException e) {
                return new ApiResponse(false, e.getMessage());
            }
            return new ApiResponse();
        }
        return new ApiResponse(false, "already liked");
    }

    @CrossOrigin
    @GetMapping("/like/{likeId}/remove")
    public ApiResponse removeLike(@PathVariable(value = "likeId") long likeId) {
        try {
            likeService.unlike(likeId);
        } catch (ActorNotFoundException e) {
            return new ApiResponse(false, e.getMessage());
        }
        return new ApiResponse();
    }

    @CrossOrigin
    @GetMapping("/likes")
    public List<UserDetails> getLikes(@RequestBody MediaContentType type, @RequestParam long itemId) {
        try {
            return likeService.getLikes(type, itemId);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


}
