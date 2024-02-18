package com.example.repository;

import com.example.entity.CommentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends CrudRepository<CommentEntity,Integer>, PagingAndSortingRepository<CommentEntity,Integer> {
    Optional<CommentEntity> findByProfileIdAndArticleId(Integer profileId, String articleID);
    Optional<CommentEntity> findByIdAndProfileId(Integer id,Integer profileId);
    List<CommentEntity> findByArticleId(String articleID);
    @Transactional
    @Modifying
    @Query("update CommentEntity set content =?1,articleId=?2,updatedDate =?3 where id=?4")
    int updateCommit(String content, String articleId, LocalDateTime now, Integer id);

    @Transactional
    @Modifying
    @Query("update CommentEntity set visible = false where id=?1 ")
    int delete (Integer id);
    @Query("select c from CommentEntity c where c.replyId=?1  ")
    List<CommentEntity> getRepliedList(Integer replyId );
}
