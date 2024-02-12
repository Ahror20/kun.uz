package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "article_type")
public class ArticleTypeEntity extends BaseEntity{
    @Column(name = "article_id",nullable = false)
    private String articleId;
    @ManyToOne
    @JoinColumn(name = "article_id",nullable = false,updatable = false,insertable = false)
    private ArticleEntity article;

    @Column(name = "type_id",nullable = false)
    private Integer typeId;
    @ManyToOne
    @JoinColumn(name = "type_id",nullable = false,updatable = false,insertable = false)
    private TypeEntity type;



}
