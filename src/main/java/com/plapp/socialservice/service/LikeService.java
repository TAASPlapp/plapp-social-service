package com.plapp.socialservice.service;

import com.plapp.entities.exceptions.ActorNotFoundException;
import com.plapp.entities.social.Like;
import com.plapp.entities.social.MediaContentType;
import com.plapp.entities.social.UserDetails;
import com.plapp.socialservice.repositories.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    public List<Like> findByMediaContentTypeAndAndItemId(MediaContentType mediaContentType, long itemId) {
        return likeRepository.findByMediaContentTypeAndAndItemId(mediaContentType, itemId);
    }

    public Like addLike(Like like) throws HibernateException,ActorNotFoundException {
        if (likeRepository.findByMediaContentTypeAndAndItemId(like.getMediaContentType(), like.getItemId()).isEmpty())
            return likeRepository.save(like);
        throw new ActorNotFoundException("Already liked");
    }

    public void unlike(long likeId) throws ActorNotFoundException {
        if (!likeRepository.existsById(likeId))
            throw new ActorNotFoundException("Like not found");
        likeRepository.deleteById(likeId);
    }

    public List<UserDetails> getLikes(MediaContentType type, long itemId) {
        List<UserDetails> users = new ArrayList<>();
        List<Like> likes = likeRepository.findByMediaContentTypeAndAndItemId(type, itemId);
        for (Like l : likes) {
            users.add(l.getAuthor());
        }
        return users;
    }

}
