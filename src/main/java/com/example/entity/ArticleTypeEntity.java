package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "article_type")
public class ArticleTypeEntity extends BaseEntity {
    @Column(name = "order_number",unique = true)
    private Integer orderNumber;
    @Column(nullable = false, name = "name_uz")
    private String nameUz;
    @Column(nullable = false,name = "name_ru")
    private String nameRu;
    @Column(nullable = false, name = "name_en")
    private String nameEn;
    @ManyToOne
    @JoinColumn(name = "article_id",nullable = false )
    private ArticleEntity article;



}
