package com.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentLikeCreateDTO {
    @NotNull
   private Integer commentId;
}
