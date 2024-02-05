package com.example.dto;

import com.example.entity.TypeEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CRUDArticleDTO {
    private String title;
    private String description;
    private String content;
    private Integer regionId;
    private Integer categoryId;
    private TypeEntity[] type;
}
