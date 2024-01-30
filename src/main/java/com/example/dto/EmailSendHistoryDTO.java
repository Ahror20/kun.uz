package com.example.dto;

import com.example.entity.ProfileEntity;
import com.example.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailSendHistoryDTO {
    private Integer id;
    private String message;
    private String email;
    private ProfileEntity profile;
    private LocalDateTime createdDate;
    private ProfileStatus status;
}
