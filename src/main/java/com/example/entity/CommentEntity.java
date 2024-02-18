package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "comment")
public class CommentEntity {
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
    @Column(name = "update_date")
    private LocalDateTime updatedDate;
    @Column(name = "content",columnDefinition = "text")
    private String content;
    @Column(name = "visible")
    private Boolean visible=true;

//    //toDo
    @Column(name = "replay_id")
    private Integer replyId;

    @ManyToOne
    @JoinColumn(name = "replay_id",insertable = false,updatable = false)
    private CommentEntity reply;

}
