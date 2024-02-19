package com.example.repository;

import com.example.entity.SavedArticleEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface SavedArticleRepository extends CrudRepository<SavedArticleEntity,Integer> {
    Optional<SavedArticleEntity> findByArticleIdAndProfileId(String articleId,Integer profileId);


    @Transactional
    @Modifying
    @Query("update SavedArticleEntity set visible=false where articleId = :articleId and profileId = :profileId and visible=true ")
    int delete(@Param("articleId") String articleId, @Param("profileId") Integer profileId);

    List<SavedArticleEntity> findByProfileId(Integer profileId);


}
