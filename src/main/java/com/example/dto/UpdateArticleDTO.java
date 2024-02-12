package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateArticleDTO {
   @NotBlank
   private String title;
   @NotBlank
   private String description;
   @NotBlank
   private String content;
   @NotNull
   private String photoId;
   @NotNull
   private Integer shared_count;
   @NotNull
   private Integer regionId;
   @NotNull
   private Integer categoryId;
   @NotNull
   private List<Integer> articleType; //
}
