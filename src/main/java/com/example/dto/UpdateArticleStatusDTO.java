package com.example.dto;

import com.example.enums.ArticleStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateArticleStatusDTO {
    @NotNull
    private ArticleStatus status;
}
