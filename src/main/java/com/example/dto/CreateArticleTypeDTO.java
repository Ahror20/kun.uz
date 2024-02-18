package com.example.dto;

import com.example.entity.ArticleEntity;
import com.example.entity.TypeEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CreateArticleTypeDTO {
    private String articleId;
    private  Integer typeId;


}
