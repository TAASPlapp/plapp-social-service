package com.plapp.socialservice.service;

import com.plapp.entities.social.UserDetails;
import com.plapp.socialservice.repositories.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    public UserDetails findByUserId(long userId) {
        return userDetailsRepository.findByUserId(userId);
    }

    public UserDetails addUser(UserDetails user) {
        return userDetailsRepository.save(user);
    }

    public UserDetails modifyUserDetail(UserDetails userDetails) {
        UserDetails user = userDetailsRepository.findByUserId(userDetails.getUserId());
        user.setBio(userDetails.getBio());
        user.setBirthdate(userDetails.getBirthdate());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setProfilePicture(userDetails.getProfilePicture());
        user.setUsername(userDetails.getUsername());
        return userDetailsRepository.save(user);
    }




}
