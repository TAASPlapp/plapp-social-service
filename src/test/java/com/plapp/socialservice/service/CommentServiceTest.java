package com.plapp.socialservice.service;

import com.plapp.entities.social.Comment;
import com.plapp.entities.social.MediaContentType;
import com.plapp.entities.social.UserDetails;
import com.plapp.socialservice.repositories.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;

    private CommentService commentService;

    @BeforeEach
    void setUp() {

        commentService = new CommentService(commentRepository);
    }


    public static Comment getTestComment() {
        return new Comment(124,
                MediaContentType.Storyboard,
                3214,
                new UserDetails(),
                "testComment",
                new Date()
        );
    }

    @Test
    void addComment() {
//        when(commentRepository.save(any(Comment.class))).then(returnsFirstArg());
//        Comment testComment = getTestComment();
//        Comment res = commentService.addComment(testComment);
//
//        assertEquals(testComment, res);
    }
}
