package com.plapp.socialservice.repositories;

import com.plapp.entities.social.Like;
import com.plapp.entities.social.MediaContentType;
import com.plapp.entities.social.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    List<Like> findByMediaContentTypeAndItemId(MediaContentType type, long itemId);
    List<Like> findByMediaContentTypeAndItemIdAndAuthor(MediaContentType type, long itemId, UserDetails author);
    boolean existsById(Long id);
    void deleteById(Long id);


}
