package com.plapp.socialservice.controllers;


import com.plapp.entities.exceptions.ActorNotFoundException;
import com.plapp.entities.social.Comment;
import com.plapp.entities.social.Like;
import com.plapp.entities.social.MediaContentType;
import com.plapp.entities.social.UserDetails;
import com.plapp.socialservice.service.CommentService;
import com.plapp.socialservice.service.LikeService;
import com.plapp.socialservice.service.UserDetailsService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @ControllerAdvice
    public static class SocialControllerAdvice {
        @ResponseStatus(HttpStatus.NOT_FOUND)
        @ExceptionHandler({ActorNotFoundException.class})
        public void handle(ActorNotFoundException e) {
        }

        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        @ExceptionHandler({HibernateException.class})
        public void handle() {
        }
    }


    @CrossOrigin
    @GetMapping("/user/{userId}")
    public UserDetails getUserDetails(@PathVariable(value = "userId") long userId) {
        return userDetailsService.findByUserId(userId);
    }

    @CrossOrigin
    @PostMapping("/user/{userId}/add")
    public UserDetails addUserDetails(@PathVariable(value = "userId") long userId,
                                      @RequestBody UserDetails user) {
        user.setUserId(userId);
        return userDetailsService.setUserDetails(user);

    }

    @CrossOrigin
    @PostMapping("/user/{userId}/update")
    public UserDetails updateUserDetails(@PathVariable long userId,
                                         @RequestBody UserDetails userDetails) {
        userDetails.setUserId(userId);
        return userDetailsService.setUserDetails(userDetails);

    }

    @CrossOrigin
    @GetMapping("/comment/{itemId}")
    public List<Comment> getComments(@RequestBody MediaContentType type,
                                     @PathVariable long itemId) {
        return commentService.findByMediaContentTypeAndItemId(type, itemId);
    }

    @CrossOrigin
    @PostMapping("/comment/{commentId}/add")
    public Comment addComment(@PathVariable(value = "commentId") long itemId,
                              @RequestBody Comment comment) {
        comment.setItemId(itemId);
        return commentService.addComment(comment);
    }

    @CrossOrigin
    @PostMapping("/like/{itemId}")
    public List<Like> getItemLikes(@PathVariable Long itemId,
                                   @RequestBody MediaContentType type) {
        return likeService.findByMediaContentTypeAndItemId(type, itemId);
    }

    @CrossOrigin
    @PostMapping("/like/{likeId}/add")
    public Like addlike(@PathVariable(value = "likeId") long itemId,
                        @RequestBody Like like) throws ActorNotFoundException {
        like.setItemId(itemId);
        return likeService.addLike(like);

    }

    @CrossOrigin
    @GetMapping("/like/{likeId}/remove")
    public void removeLike(@PathVariable(value = "likeId") long likeId) throws ActorNotFoundException {
        likeService.unlike(likeId);
    }

    @CrossOrigin
    @GetMapping("/like/{itemId}/users")
    public List<UserDetails> getLikes(@RequestParam MediaContentType type,
                                      @PathVariable(value = "itemId") long itemId) {
        return likeService.getLikes(type, itemId);
    }


}
