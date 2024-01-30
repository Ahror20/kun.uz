package com.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCategoryDTO {
    private Integer order_number;
    private String nameUz;
    private String nameRu;
    private String nameEn;
}
