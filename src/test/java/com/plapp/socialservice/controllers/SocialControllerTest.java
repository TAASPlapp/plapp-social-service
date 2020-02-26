package com.plapp.socialservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plapp.entities.exceptions.ActorNotFoundException;
import com.plapp.entities.social.Comment;
import com.plapp.entities.social.Like;
import com.plapp.entities.social.MediaContentType;
import com.plapp.entities.social.UserDetails;
import com.plapp.entities.utils.ApiResponse;
import com.plapp.socialservice.service.*;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SocialController.class)
class SocialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    CommentService commentService;

    @MockBean
    LikeService likeService;

    @MockBean
    UserDetailsService userDetailsService;


    @Test
    void getUserDetails_validInput() throws Exception {
        long userId = 13420;
        mockMvc.perform(get("/social/user/{userId}", userId))
                .andExpect(status().isOk());
    }

    @Test
    void getUserDetails_invalidInput() throws Exception {
        mockMvc.perform(get("/social/user/userId"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getUserDetails_notExistingInput() throws Exception {
        long userId = -1;
        MvcResult result = mockMvc.perform(get("/social/user/{userId}", userId))
                .andExpect(status().isOk())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        assertThat(responseBody).isEqualTo("");
    }

    @Test
    void addUserDetails_validInput() throws Exception {
        UserDetails testUser = UserDetailsServiceTest.getTestUser();
        mockMvc.perform(post("/social/user/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk());
    }

    @Test
    void addUserDetails_validInput_returnsApiResponse() throws Exception {
        UserDetails testUser = UserDetailsServiceTest.getTestUser();
        MvcResult mvcResult = mockMvc.perform(post("/social/user/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse expectedResponse = new ApiResponse(true, "User added successfully");
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(objectMapper.writeValueAsString(expectedResponse)).isEqualToIgnoringWhitespace(actualResponse);
    }

    @Test
    void addUserDetails_invalidInput() throws Exception {
        mockMvc.perform(post("/social/user/add")
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }


    @Test
    void updateUserDetails_validInput() throws Exception {
        UserDetails testUser = UserDetailsServiceTest.getTestUser();
        mockMvc.perform(post("/social/user/update")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk());
    }

    @Test
    void updateUserDetails_validInput_returnsApiResponse() throws Exception {
        UserDetails testUser = UserDetailsServiceTest.getTestUser();
        MvcResult mvcResult = mockMvc.perform(post("/social/user/update")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse expectedResponse = new ApiResponse(true, "User updated successfully");
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(objectMapper.writeValueAsString(expectedResponse)).isEqualToIgnoringWhitespace(actualResponse);
    }

    @Test
    void updateUserDetails_invalidInput() throws Exception {
        mockMvc.perform(post("/social/user/update")
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getComments_validInput() throws Exception {
        long itemId = 34352;

        MediaContentType mediaContentType = MediaContentType.Storyboard;
        mockMvc.perform(get("/social/comment/{itemId}", itemId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(mediaContentType)))
                .andExpect(status().isOk());
    }

    @Test
    void getComments_invalidInput() throws Exception {
        long itemId = 3241;

        MediaContentType mediaContentType = MediaContentType.Storyboard;
        mockMvc.perform(get("/social/comment/{itemId}", itemId)
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getComments_nonExistingInput() throws Exception {
        long itemId = -1;

        MediaContentType mediaContentType = MediaContentType.Storyboard;
        MvcResult mvcResult = mockMvc.perform(get("/social/comment/{itemId}", itemId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(mediaContentType)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        assertThat(responseBody).isEqualTo("[]");
    }

    @Test
    void addComment_validInput() throws Exception {
        Comment testComment = CommentServiceTest.getTestComment();
        mockMvc.perform(post("/social/comment/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testComment)))
                .andExpect(status().isOk());

    }

    @Test
    void addComment_validInput_returnsApiResponse() throws Exception {
        Comment testComment = CommentServiceTest.getTestComment();
        MvcResult mvcResult = mockMvc.perform(post("/social/comment/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testComment)))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse expectedResponse = new ApiResponse(true, "Comment added successfully");
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(objectMapper.writeValueAsString(expectedResponse)).isEqualToIgnoringWhitespace(actualResponse);
    }

    @Test
    void addComment_invalidInput() throws Exception {
        Comment testComment = CommentServiceTest.getTestComment();
        mockMvc.perform(post("/social/comment/add")
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addlike_validInput() throws Exception {
        when(likeService.addLike(any(Like.class))).then(returnsFirstArg());
        Like testLike = LikeServiceTest.getTestLike();
        mockMvc.perform(post("/social/like/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testLike)))
                .andExpect(status().isOk());
    }

    @Test
    void addlike_validInput_returns_positive__ApiResponse() throws Exception {
        when(likeService.addLike(any(Like.class))).then(returnsFirstArg());
        Like testLike = LikeServiceTest.getTestLike();
        MvcResult mvcResult = mockMvc.perform(post("/social/like/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testLike)))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse expectedResponse = new ApiResponse(true, "Like added successfully");
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(objectMapper.writeValueAsString(expectedResponse)).isEqualToIgnoringWhitespace(actualResponse);
    }

    @Test
    void addlike_validInput_returns_already_liked_ApiResponse() throws Exception {
        Like testLike = LikeServiceTest.getTestLike();
        when(likeService.addLike(any(Like.class))).thenThrow(new ActorNotFoundException("Already liked"));
        MvcResult mvcResult = mockMvc.perform(post("/social/like/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testLike)))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse expectedResponse = new ApiResponse(false, "Already liked");
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(objectMapper.writeValueAsString(expectedResponse)).isEqualToIgnoringWhitespace(actualResponse);
    }

    @Test
    void addlike_invalidInput() throws Exception {
        mockMvc.perform(post("/social/like/add")
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void removeLike_validInput() throws Exception {
        long likeId = 1213;
        mockMvc.perform(get("/social/like/{userId}/remove", likeId))
                .andExpect(status().isOk());
    }

    @Test
    void removeLike_validInput_ApiResponse() throws Exception {
        long likeId = 1213;
        MvcResult mvcResult = mockMvc.perform(get("/social/like/{userId}/remove", likeId))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse expectedResponse = new ApiResponse(true, "Like removed successfully");
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(objectMapper.writeValueAsString(expectedResponse)).isEqualToIgnoringWhitespace(actualResponse);
    }

    @Test
    void removeLike_validInput_ActorNotFound_ApiResponse() throws Exception {
        long likeId = 1213;
        doThrow(new ActorNotFoundException("Like not found")).when(likeService).unlike(any(Long.class));
        MvcResult mvcResult = mockMvc.perform(get("/social/like/{userId}/remove", likeId))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse expectedResponse = new ApiResponse(false, "Like not found");
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(objectMapper.writeValueAsString(expectedResponse)).isEqualToIgnoringWhitespace(actualResponse);
    }

    @Test
    void removeLike_invalidInput() throws Exception {
        mockMvc.perform(get("/social/like/userId/remove"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getLikes_validInput() throws Exception {
        MediaContentType mct = MediaContentType.Storyboard;
        long likeId = 131;

        mockMvc.perform(get("/social/like/{likeId}/users", likeId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(mct)))
                .andExpect(status().isOk());
    }

    @Test
    void getLikes_invalidInput() throws Exception {
        long likeId = 131;

        mockMvc.perform(get("/social/like/{likeId}/users", likeId))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getLikes_not_existing_Input() throws Exception {
        long likeId = -1;
        mockMvc.perform(get("/social/like/{likeId}/users", likeId))
                .andExpect(status().isBadRequest());
    }


    //Hibernate Exception handler test

    @Test
    void addUserDetails_throwsException() throws Exception {
        doThrow(new HibernateException("hibernate exception")).when(userDetailsService).addUser(any(UserDetails.class));
        UserDetails testUser = UserDetailsServiceTest.getTestUser();

        MvcResult mvcResult = mockMvc.perform(post("/social/user/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse expectedResponse = new ApiResponse(false, "hibernate exception");
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(objectMapper.writeValueAsString(expectedResponse)).isEqualToIgnoringWhitespace(actualResponse);
    }

    @Test
    void updateUserDetails_throwsException() throws Exception {
        doThrow(new HibernateException("hibernate exception")).when(userDetailsService).modifyUserDetail(any(UserDetails.class));
        UserDetails testUser = UserDetailsServiceTest.getTestUser();

        MvcResult mvcResult = mockMvc.perform(post("/social/user/update")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse expectedResponse = new ApiResponse(false, "hibernate exception");
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(objectMapper.writeValueAsString(expectedResponse)).isEqualToIgnoringWhitespace(actualResponse);
    }

    @Test
    void addComment_throwsException() throws Exception {
        doThrow(new HibernateException("hibernate exception")).when(commentService).addComment(any(Comment.class));
        Comment testComment = CommentServiceTest.getTestComment();

        MvcResult mvcResult = mockMvc.perform(post("/social/comment/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testComment)))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse expectedResponse = new ApiResponse(false, "hibernate exception");
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(objectMapper.writeValueAsString(expectedResponse)).isEqualToIgnoringWhitespace(actualResponse);
    }

    @Test
    void addlike_throwsException() throws Exception {
        doThrow(new HibernateException("hibernate exception")).when(likeService).addLike(any(Like.class));
        Like testLike = LikeServiceTest.getTestLike();

        MvcResult mvcResult = mockMvc.perform(post("/social/like/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testLike)))
                .andExpect(status().isOk())
                .andReturn();

        ApiResponse expectedResponse = new ApiResponse(false, "hibernate exception");
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(objectMapper.writeValueAsString(expectedResponse)).isEqualToIgnoringWhitespace(actualResponse);
    }

}
