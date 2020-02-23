package com.plapp.socialservice.repositories;
import com.plapp.entities.social.Comment;
import com.plapp.entities.social.MediaContentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
    List<Comment> findByMediaContentTypeAndAndItemId(MediaContentType type, long itemId);
}
