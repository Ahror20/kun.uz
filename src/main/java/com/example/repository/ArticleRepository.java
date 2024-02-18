package com.example.repository;

import com.example.entity.ArticleEntity;
import com.example.enums.ArticleStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleRepository extends CrudRepository<ArticleEntity, String> {
    @Transactional
    @Modifying
    @Query("update ArticleEntity set visible = false where id =:id")
    int delete(@Param("id") String id);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set title =?1, description =?2, content =?3, sharedCount =?4, photoID =?5, regionId =?6, categoryId =?7, moderatorId =?8 where id =?9")
    int update(String title, String description, String content, Integer sharedCount, String photoID, Integer regionId, Integer categoryId, Integer moderatorId, String id);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set status=?1, publisherId=?2,publishedDate=?3 where id=?4")
    void changeStatusById(ArticleStatus status, Integer publisherId, LocalDateTime publishedDate, String id);

    @Query(value = "SELECT a.* FROM article a WHERE a.id NOT IN :id order by a.created_date desc LIMIT 8", nativeQuery = true)
    List<ArticleEntity> get8Article(@Param("id") String[] id);

    @Query(value = "select a.* from article a inner join article_type at on at.article_id=a.id \n" +
            "where at.type_id = :typeId \n" +
            "and a.id NOT IN :id " +
            "order by created_date desc \n" +
            "limit 4 ",nativeQuery = true)
    List<ArticleEntity> getLastArticle4ArticleByTypes(@Param("typeId") Integer typeId,
                                                      @Param("id") String[] id);

    @Query(value = "select * from article order by view_count desc limit 4",nativeQuery = true)
    List<ArticleEntity> get4MostReadArticles();
}
