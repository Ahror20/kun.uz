package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class GetRepliedListDTO {
   private Integer id;
   private LocalDateTime createDate;
   private LocalDateTime updateDate;
   private Integer profileId;
   private String profileName;
   private String profileSurname;
}
