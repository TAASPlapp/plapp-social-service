package com.plapp.socialservice.service;

import com.plapp.entities.social.Comment;
import com.plapp.entities.social.MediaContentType;
import com.plapp.socialservice.repositories.CommentRepository;
import org.hibernate.HibernateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> findByMediaContentTypeAndAndItemId(MediaContentType type, long itemId) {
        return commentRepository.findByMediaContentTypeAndAndItemId(type, itemId);
    }

    public Comment addComment(Comment comment) throws HibernateException {
        return commentRepository.save(comment);
    }
}
