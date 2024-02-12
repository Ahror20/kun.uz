package com.example.entity;

import com.example.enums.LikeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "article_like")
public class ArticleLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "profile_id",nullable = false)
    private Integer profileId;
    @ManyToOne
    @JoinColumn(name = "profile_id",updatable = false ,insertable = false)
    private ProfileEntity profile;
    @Column(name = "article_id",nullable = false)
    private String articleId;
    @ManyToOne
    @JoinColumn(name = "article_id",updatable = false,insertable = false)
    private ArticleEntity article;
    @Column(name = "created_date")
    private LocalDateTime createdDate=LocalDateTime.now();
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private LikeStatus status;

}
