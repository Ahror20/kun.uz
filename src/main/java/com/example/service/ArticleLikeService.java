package com.example.service;

import com.example.dto.ArticleLikeDTO;
import com.example.dto.CreateArticleLikeDTO;
import com.example.entity.ArticleLikeEntity;
import com.example.enums.LikeStatus;
import com.example.repository.ArticleLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleLikeService {
    @Autowired
    private ArticleLikeRepository articleLikeRepository;
    public ArticleLikeDTO like(CreateArticleLikeDTO dto, Integer profileId) {
        checkArticleLike(dto.getArticleId(), profileId);
        ArticleLikeEntity entity = new ArticleLikeEntity();
        entity.setArticleId(dto.getArticleId());
        entity.setProfileId(profileId);
        entity.setStatus(LikeStatus.LIKE);
        articleLikeRepository.save(entity);
        return toDTO(entity);
    }
    public ArticleLikeDTO dislike(CreateArticleLikeDTO dto, Integer profileId) {
        checkArticleLike(dto.getArticleId(), profileId);
        ArticleLikeEntity entity = new ArticleLikeEntity();
        entity.setArticleId(dto.getArticleId());
        entity.setProfileId(profileId);
        entity.setStatus(LikeStatus.DISLIKE);
        articleLikeRepository.save(entity);
        return toDTO(entity);

    }

    private ArticleLikeDTO toDTO(ArticleLikeEntity entity){
        ArticleLikeDTO dto = new ArticleLikeDTO();
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setArticleId(entity.getArticleId());
        dto.setProfileId(entity.getProfileId());
        dto.setStatus(entity.getStatus());
        return dto;

    }

    public Boolean remove(String articleId, Integer profileId) {
        checkArticleLike(articleId,profileId);
        return true;
    }
    private void checkArticleLike(String articleId, Integer profileId){
        Optional<ArticleLikeEntity> optional = articleLikeRepository.findByProfileIdAndArticleId(profileId, articleId);
        optional.ifPresent(entity -> articleLikeRepository.delete(entity));
    }
}
