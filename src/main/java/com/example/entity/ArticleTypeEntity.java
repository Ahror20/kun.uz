package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "article_type")
public class ArticleTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "article_id",nullable = false)
    private ArticleEntity article;
    @ManyToOne
    @JoinColumn(name = "type_id",nullable = false)
    private TypeEntity type;
    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private ProfileEntity publisher;
    @Column(name = "created_date")
    private LocalDateTime createdDate=LocalDateTime.now();

}
