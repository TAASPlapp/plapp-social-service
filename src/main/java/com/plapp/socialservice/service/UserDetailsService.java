package com.plapp.socialservice.service;

import com.plapp.entities.social.UserDetails;
import com.plapp.socialservice.repositories.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    public UserDetails findByUserId(long userId) throws HibernateException {
        return userDetailsRepository.findByUserId(userId);
    }

    public UserDetails setUserDetails(UserDetails user) throws HibernateException {
        return userDetailsRepository.save(user);
    }

}
