package com.example.repository;

import com.example.dto.CommentFilterDTO;
import com.example.dto.PaginationResultDTO;
import com.example.dto.ProfileFilterDTO;
import com.example.entity.CommentEntity;
import com.example.entity.ProfileEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentCustomRepository {
    @Autowired
    private EntityManager entityManager;
    public PaginationResultDTO<CommentEntity> filter(CommentFilterDTO filter, int page, int size) {
        StringBuilder builder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        if (filter.getId() != null) {
            builder.append("and id =:id ");
            params.put("id", filter.getId());
        }
        if (filter.getProfileId() != null) {
            builder.append("and s.profileId =:profileId ");
            params.put("profileId", filter.getProfileId());
        }
        if (filter.getArticleId() != null) {
            builder.append("and s.articleId =:articleId  ");
            params.put("articleId", filter.getArticleId());
        }
        if (filter.getCreatedDateFrom() != null && filter.getCreatedDateTo() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filter.getCreatedDateFrom(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filter.getCreatedDateTo(), LocalTime.MAX);
            builder.append("and s.createdDate between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (filter.getCreatedDateFrom() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filter.getCreatedDateFrom(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filter.getCreatedDateTo(), LocalTime.MAX);
            builder.append("and s.createdDate between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (filter.getCreatedDateTo() != null) {
            LocalDateTime toDate = LocalDateTime.of(filter.getCreatedDateTo(), LocalTime.MAX);
            builder.append("and createdDate <= :toDate ");
            params.put("toDate", toDate);
        }

        StringBuilder selectBuilder = new StringBuilder("FROM CommentEntity s where 1=1 ");
        selectBuilder.append(builder);
        selectBuilder.append(" order by createdDate desc");

        StringBuilder countBuilder = new StringBuilder("Select count(s) FROM CommentEntity s where 1=1 ");
        countBuilder.append(builder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        selectQuery.setMaxResults(size);
        selectQuery.setFirstResult((page-1) * size);

        Query countQuery = entityManager.createQuery(countBuilder.toString());

        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }
        List<CommentEntity> entityList = selectQuery.getResultList();
        Long totalElements = (Long) countQuery.getSingleResult();

        return new PaginationResultDTO<>(totalElements, entityList);
    }
}
