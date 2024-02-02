package com.example.entity;

import com.example.enums.ArticleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "article")
public class ArticleEntity {
    @Id
    private String id = UUID.randomUUID().toString();
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false, columnDefinition = "text")
    private String content;
    @Column(nullable = false, name = "shared_count")
    private Integer sharedCount=0;
    @ManyToOne
    @JoinColumn(name = "region_id")
    private RegionEntity region;
    @ManyToOne
    @JoinColumn(name = "moderator_id",nullable = false)
    private ProfileEntity moderator;
    @ManyToOne
    @JoinColumn(name = "publisher_id",nullable = false)
    private ProfileEntity publisher;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
    @Column(nullable = false)
    private ArticleStatus status;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    @Column(name = "published_date", nullable = false)
    private LocalDateTime publishedDate;
    @Column(name = "visible")
    private boolean visible = true;

    @OneToMany(fetch = FetchType.LAZY)
    private List<ArticleTypeEntity> typeList;



}
