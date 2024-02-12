package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionDTO {
    private Integer id;

    @NotNull(message = "OrderNumber required")
    private Integer order_number;
    @NotNull(message = "OrderNumber required")
    private String nameUz;
    @Size(min = 10,max = 200, message = "NameUz must be between 10 and 200 characters")
    @NotBlank(message = "nameRu must be between 10 and 200 characters")
    private String nameRu;
    private String nameEn;
    private String name;

    private Boolean visible;
    protected LocalDateTime updatedDate;
    protected LocalDateTime createdDate;
}
