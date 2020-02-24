package com.plapp.socialservice.repositories;

import com.plapp.entities.social.Like;
import com.plapp.entities.social.MediaContentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    List<Like> findByMediaContentTypeAndAndItemId(MediaContentType type, long itemId);
    boolean existsById(Long id);
    void deleteById(Long id);


}
