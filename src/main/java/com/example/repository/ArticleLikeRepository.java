package com.example.repository;

import com.example.entity.ArticleLikeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ArticleLikeRepository extends CrudRepository<ArticleLikeEntity,Integer> {
    Optional<ArticleLikeEntity> findByProfileIdAndArticleId(Integer profileId, String articleId);
}
