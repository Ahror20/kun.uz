package com.example.repository;

import com.example.entity.ArticleLikeEntity;
import com.example.entity.CommentLikeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity,Integer> {
    Optional<CommentLikeEntity> findByProfileIdAndCommentId(Integer profileId, Integer commentId);
}
