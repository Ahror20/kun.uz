package com.example.dto;

import com.example.entity.CategoryEntity;
import com.example.entity.ProfileEntity;
import com.example.entity.RegionEntity;
import com.example.enums.ArticleStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {
    private String id;
    private String title;
    private String description;
    private String content;
    private Integer sharedCount;
    private RegionEntity region;
    private ProfileEntity moderator;
    private ProfileEntity publisher;
    private CategoryEntity category;
    private ArticleStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
    private boolean visible;
}
