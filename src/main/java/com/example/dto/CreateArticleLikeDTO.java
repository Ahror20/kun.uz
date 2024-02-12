package com.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateArticleLikeDTO {
    @NotNull
    private String articleId;
}
