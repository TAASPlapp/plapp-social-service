package com.plapp.socialservice.controllers;


import com.plapp.entities.social.Comment;
import com.plapp.entities.social.Like;
import com.plapp.entities.social.MediaContentType;
import com.plapp.entities.social.UserDetails;
import com.plapp.entities.utils.ApiResponse;
import com.plapp.socialservice.repositories.CommentRepository;
import com.plapp.socialservice.repositories.LikeRepository;
import com.plapp.socialservice.repositories.UserDetailsRepository;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/social")
public class SocialController {

    private CommentRepository commentRepository;
    private UserDetailsRepository userDetailsRepository;
    private LikeRepository likeRepository;


    @Autowired
    public SocialController(CommentRepository commentRepository,
                            UserDetailsRepository userDetailsRepository,
                            LikeRepository likeRepository) {
        this.commentRepository = commentRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.likeRepository = likeRepository;
    }


    @CrossOrigin
    @GetMapping("/user/{userId}")
    public UserDetails getUserDetails(@PathVariable(value = "userId") long userId) throws Exception {
        return userDetailsRepository.findByUserId(userId);
    }

    @CrossOrigin
    @PostMapping("/user/add")
    public ApiResponse addUserDetails(@RequestBody UserDetails user) throws Exception {
        try {
            userDetailsRepository.save(user);
        } catch (HibernateException e) {
            return new ApiResponse(false, e.getMessage());
        }
        return new ApiResponse(true, "user datails updated");
    }

    @CrossOrigin
    @PostMapping("/user/modify")
    public ApiResponse setUserDetails(@RequestBody UserDetails userDetails) throws Exception {
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
    public List<Comment> getComments(@RequestBody MediaContentType type, @RequestParam long itemId) throws Exception {
        return commentRepository.findByMediaContentTypeAndAndItemId(type, itemId);
    }

    @CrossOrigin
    @PostMapping("/comment/add")
    public ApiResponse addComment(@RequestBody Comment comment) throws Exception {
        try {
            commentRepository.save(comment);
        } catch (HibernateException e) {
            return new ApiResponse(false, e.getMessage());
        }
        return new ApiResponse();
    }

    @CrossOrigin
    @PostMapping("/like/add")
    public ApiResponse addlike(@RequestParam Like like) throws Exception {
        if (likeRepository.findByMediaContentTypeAndAndItemId(like.getMediaContentType(), like.getItemId()).isEmpty()) {
            try {
                likeRepository.save(like);
            } catch (HibernateException e) {
                return new ApiResponse(false, e.getMessage());
            }
            return new ApiResponse();
        }
        return new ApiResponse(false, "already liked");
    }

    @CrossOrigin
    @GetMapping("/like/{likeId}/remove")
    public ApiResponse unlike(@PathVariable(value = "likeId") long likeId) throws Exception {
        if (!likeRepository.existsById(likeId))
            return new ApiResponse(false, "Like does not exist");
        likeRepository.deleteById(likeId);
        return new ApiResponse();
    }

    @CrossOrigin
    @GetMapping("/likes")
    public List<UserDetails> getLikes(@RequestBody MediaContentType type, @RequestParam long itemId) throws Exception {
        List<UserDetails> users = new ArrayList<>();
        List<Like> likes = likeRepository.findByMediaContentTypeAndAndItemId(type, itemId);
        for (Like l : likes) {
            users.add(l.getAuthor());
        }
        return users;
    }


}
