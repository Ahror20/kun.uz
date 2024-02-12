package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateArticleDTO {
    @NotBlank(message = "title must be between 10 and 200 characters")
    @Size(min = 10)
    private String title;
    @NotBlank(message = "description required")
    @Size(min = 10)
    private String description;
    @NotBlank(message = "regionId required")
    @Size(min = 20)
    private String content;
    @NotNull(message = "regionId required")
    private Integer regionId;
    @NotNull(message = "categoryId required")
    private Integer categoryId;
    @NotNull
    private List<Integer> articleType; //
    @NotNull
    private String photoId;


}
