package com.example.dto;

import com.example.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class ArticleFilterDTO {
   private String id;
   private String title;
   private Integer regionId;
   private Integer categoryId;
   private LocalDate cratedDateFrom;
   private LocalDate createDateTo;
   private LocalDate publishedDateFrom;
   private LocalDate publishedDateTo;
   private Integer moderatorId;
   private Integer publisherId;
   private ArticleStatus status;
}
