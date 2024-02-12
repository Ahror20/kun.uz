package com.example.entity;

import com.example.enums.ArticleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "article")
public class ArticleEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false, columnDefinition = "text")
    private String content;
    @Column(nullable = false, name = "shared_count")
    private Integer sharedCount;

    @Column(name = "region_id")
    private Integer regionId;
    @ManyToOne
    @JoinColumn(name = "region_id",insertable = false,updatable = false)
    private RegionEntity region;

    @Column(name = "moderator_id")
    private Integer moderatorId;
    @ManyToOne
    @JoinColumn(name = "moderator_id",nullable = false,insertable = false,updatable = false)
    private ProfileEntity moderator;

    @Column(name = "publisher_id")
    private Integer publisherId;
    @ManyToOne
    @JoinColumn(name = "publisher_id",insertable = false,updatable = false)
    private ProfileEntity publisher;

    @Column(name = "photo_id")
    private String photoID;
    @ManyToOne
    @JoinColumn(name = "photo_id",insertable = false,updatable = false,nullable = false)
    private AttachEntity photo;

    @Column(name = "category_id")
    private Integer categoryId ;
    @ManyToOne
    @JoinColumn(name = "category_id",insertable = false,updatable = false )
    private CategoryEntity category;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ArticleStatus status;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    @Column(name = "published_date")
    private LocalDateTime publishedDate;
    @Column(name = "visible")
    private Boolean visible = true;
    @Column(name = "view_count")
    private Integer viewCount;




}
