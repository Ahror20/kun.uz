package com.example.dto;

import com.example.enums.SmsStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SmsHistoryDTO {
    private Integer id;
    private String phone;
    private String message;
    private String type;
    private SmsStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime usedDate;

}
