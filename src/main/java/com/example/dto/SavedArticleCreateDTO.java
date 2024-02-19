package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
public class SavedArticleCreateDTO {
    @NotNull
    private String articleId;
}
