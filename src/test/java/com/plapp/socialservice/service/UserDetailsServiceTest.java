package com.plapp.socialservice.service;

import com.plapp.entities.social.UserDetails;
import com.plapp.socialservice.repositories.UserDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {

    @Mock
    private UserDetailsRepository userDetailsRepository;

    private UserDetailsService userService;

    @BeforeEach
    void setUp() {
        userService = new UserDetailsService(userDetailsRepository);
    }

    public static UserDetails getTestUser() {
        return new UserDetails(
                21,
                "testuser",
                "test",
                "user",
                "testuser is a test user",
                new Date(),
                "http://test-image.png"
        );
    }

    @Test
    void test_addUser() {
        when(userDetailsRepository.save(any(UserDetails.class))).then(returnsFirstArg());
        UserDetails testUser = getTestUser();

        UserDetails savedUser = userService.addUser(testUser);
        assertThat(savedUser.getFirstName()).isNotNull().isEqualTo(testUser.getFirstName());
        assertThat(savedUser.getLastName()).isNotNull().isEqualTo(testUser.getLastName());
        assertThat(savedUser.getUsername()).isNotNull().isEqualTo(testUser.getUsername());
        assertThat(savedUser.getBio()).isNotNull().isEqualTo(testUser.getBio());
        assertThat(savedUser.getBirthdate()).isNotNull().isEqualTo(testUser.getBirthdate());
        assertThat(savedUser.getProfilePicture()).isNotNull().isEqualTo(testUser.getProfilePicture());
    }

    @Test
    void test_modifyUserDetail() {
        UserDetails user1 = getTestUser();
        UserDetails user2 = getTestUser();
        when(userDetailsRepository.findByUserId(any(long.class))).thenReturn(user1);
        when(userDetailsRepository.save(any(UserDetails.class))).then(returnsFirstArg());

        user2.setUsername("mod-username");
        user2.setBio("bio-mod-username");

        UserDetails modUser = userService.modifyUserDetail(user2);
        assertThat(modUser.getUsername()).isEqualTo(user2.getUsername());
        assertThat(modUser.getBio()).isEqualTo(user2.getBio());

    }
}
