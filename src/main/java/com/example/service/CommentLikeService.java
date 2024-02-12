package com.example.service;

import com.example.dto.ArticleLikeDTO;
import com.example.dto.CommentLikeCreateDTO;
import com.example.dto.CommentLikeDTO;
import com.example.entity.ArticleLikeEntity;
import com.example.entity.CommentLikeEntity;
import com.example.enums.LikeStatus;
import com.example.repository.CommentLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentLikeService {
    @Autowired
    private CommentLikeRepository commentLikeRepository;
    public CommentLikeDTO like(CommentLikeCreateDTO dto, Integer profileId) {
        checkArticleLike(dto.getCommentId(), profileId);
        CommentLikeEntity entity = new CommentLikeEntity();
        entity.setCommentId(dto.getCommentId());
        entity.setProfileId(profileId);
        entity.setStatus(LikeStatus.LIKE);
        commentLikeRepository.save(entity);
        return toDTO(entity);

    }
    public CommentLikeDTO dislike(CommentLikeCreateDTO dto, Integer profileId) {
        checkArticleLike(dto.getCommentId(), profileId);
        CommentLikeEntity entity = new CommentLikeEntity();
        entity.setCommentId(dto.getCommentId());
        entity.setProfileId(profileId);
        entity.setStatus(LikeStatus.DISLIKE);
        commentLikeRepository.save(entity);
        return toDTO(entity);

    }
    public Boolean remove(Integer commentId, Integer profileId) {
        checkArticleLike(commentId,profileId);
        return true;
    }
    private CommentLikeDTO toDTO(CommentLikeEntity entity){
        CommentLikeDTO dto = new CommentLikeDTO();
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCommentId(entity.getCommentId());
        dto.setProfileId(entity.getProfileId());
        dto.setStatus(entity.getStatus());
        return dto;

    }
    private void checkArticleLike(Integer commentId, Integer profileId){
        Optional<CommentLikeEntity> optional = commentLikeRepository.findByProfileIdAndCommentId(profileId, commentId);
        optional.ifPresent(entity -> commentLikeRepository.delete(entity));
    }
}
