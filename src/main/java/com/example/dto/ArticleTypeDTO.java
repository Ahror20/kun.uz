package com.example.dto;

import com.example.entity.ArticleEntity;
import com.example.entity.ProfileEntity;
import com.example.entity.TypeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ArticleTypeDTO {
    private Integer id;
    private ArticleEntity article;
    private TypeEntity type;
    private ProfileEntity publisher;
    private LocalDateTime createdDate;
}
