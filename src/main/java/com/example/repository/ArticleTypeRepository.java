package com.example.repository;

import com.example.entity.ArticleTypeEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity, Integer> {
    @Query("SELECT nat FROM ArticleTypeEntity AS nat WHERE nat.articleId=?1 AND nat.typeId=?2 AND nat.visible=true ")
    Optional<ArticleTypeEntity> getArticleTypeId(String articleId, Integer articleTypeId);

    @Query("SELECT nat FROM ArticleTypeEntity AS nat WHERE nat.articleId=?1 AND nat.visible=true")
    List<ArticleTypeEntity> findArticleId(String articleId);

    @Transactional
    @Modifying
    @Query("delete from ArticleTypeEntity AS nat  WHERE nat.articleId=:articleId AND nat.typeId=:articleTypeId")
    void deleteEn(@Param("articleId") String articleId, @Param("articleTypeId") Integer articleTypeId);

    @Transactional
    @Modifying
    @Query("update ArticleTypeEntity set updatedDate =?1  where articleId =?2 and typeId=?3")
    void updateDate(LocalDateTime updatedDate, String articleId, Integer typeId);

    @Query("select at from ArticleTypeEntity at where at.typeId=?1 order by at.createdDate DESC limit ?2")
    List<ArticleTypeEntity> findTop5ByTypes(Integer typeId,Integer limit);

    List<ArticleTypeEntity> findByTypeId(Integer id);


}
