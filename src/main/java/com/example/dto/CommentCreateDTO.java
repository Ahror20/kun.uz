package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateDTO {
    @NotBlank
    private  String content;
    @NotNull
    private String articleId;
//    @NotNull
//    private Integer replyId;
}
