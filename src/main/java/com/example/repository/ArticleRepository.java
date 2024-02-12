package com.example.repository;

import com.example.entity.ArticleEntity;
import com.example.enums.ArticleStatus;
import com.example.enums.ProfileStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

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
}
