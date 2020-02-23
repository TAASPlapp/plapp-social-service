package com.plapp.socialservice.repositories;

import com.plapp.entities.social.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {
    UserDetails findByUserId(long userId);
}
