package com.plapp.socialservice.service;

import com.plapp.entities.social.Like;
import com.plapp.entities.social.MediaContentType;
import com.plapp.entities.social.UserDetails;
import com.plapp.socialservice.repositories.LikeRepository;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {
    @Mock
    private LikeRepository likeRepository;

    private LikeService likeService;

    @BeforeEach
    void setUp() {
        likeService = new LikeService(likeRepository);
    }

    public static Like getTestLike(){
        return new Like(
                123,
                MediaContentType.Storyboard,
                2131,
                new UserDetails(
                        21,
                        "testuser",
                        "test",
                        "user",
                        "testuser is a test user",
                        new Date(),
                        "http://test-image.png"
                ),
                new Date()
        );
    }

    @Test
    void addLike() {
        when(likeRepository.save(any(Like.class))).then(returnsFirstArg());
        Like testLike = getTestLike();
        Like res = likeService.addLike(testLike);
        assertEquals(testLike,res);
    }

    @Test
    void testNotExistingUnlike() {
        when(likeRepository.existsById(any(Long.class))).thenReturn(false);
        assertThrows(HibernateException.class, ()->{
            likeService.unlike(123);
        });
    }

    @Test
    void getLikes() {
        ArrayList<Like> likeList = new ArrayList<>();
        Like testLike = getTestLike();
        likeList.add(testLike);
        when(likeRepository.findByMediaContentTypeAndAndItemId(any(MediaContentType.class),any(Long.class)))
                .thenReturn(likeList);
        ArrayList<UserDetails> users = (ArrayList<UserDetails>) likeService.getLikes(MediaContentType.Storyboard,2131);
        assertEquals(users.get(0),testLike.getAuthor());
    }
}
