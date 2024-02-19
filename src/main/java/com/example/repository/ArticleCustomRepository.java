package com.example.repository;

import com.example.dto.ArticleFilterDTO;
import com.example.dto.PaginationResultDTO;
import com.example.dto.ProfileFilterDTO;
import com.example.entity.ArticleEntity;
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
public class ArticleCustomRepository {
    @Autowired
    private EntityManager entityManager;
    public PaginationResultDTO<ArticleEntity> filter(ArticleFilterDTO filter, int page, int size) {
        StringBuilder builder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        if (filter.getId() != null) {
            builder.append("and id =:id ");
            params.put("id", filter.getId());
        }
        if (filter.getTitle() != null) {
            builder.append("and title =:title ");
            params.put("title", filter.getTitle());
        }
        if (filter.getStatus() != null) {
            builder.append("and status :status ");
            params.put("status", filter.getStatus() );
        }
        if (filter.getCratedDateFrom() != null && filter.getCratedDateFrom() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filter.getPublishedDateFrom(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filter.getCreateDateTo(), LocalTime.MAX);
            builder.append("and createdDate between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (filter.getCratedDateFrom() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filter.getCratedDateFrom(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filter.getCreateDateTo(), LocalTime.MAX);
            builder.append("and createdDate between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        } else if (filter.getCreateDateTo() != null) {
            LocalDateTime toDate = LocalDateTime.of(filter.getCreateDateTo(), LocalTime.MAX);
            builder.append("and createdDate <= :toDate ");
            params.put("toDate", toDate);
        }

        StringBuilder selectBuilder = new StringBuilder("FROM ArticleEntity s where 1=1 ");
        selectBuilder.append(builder);
        selectBuilder.append(" order by createdDate desc");

        StringBuilder countBuilder = new StringBuilder("Select count(s) FROM ArticleEntity s where 1=1 ");
        countBuilder.append(builder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        selectQuery.setMaxResults(size);
        selectQuery.setFirstResult((page-1) * size);

        Query countQuery = entityManager.createQuery(countBuilder.toString());

        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }
        List<ArticleEntity> entityList = selectQuery.getResultList();
        Long totalElements = (Long) countQuery.getSingleResult();

        return new PaginationResultDTO<>(totalElements, entityList);
    }
}
