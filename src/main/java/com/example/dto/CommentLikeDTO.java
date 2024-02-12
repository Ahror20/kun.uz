package com.example.dto;

import com.example.entity.CommentEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.LikeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class CommentLikeDTO {
    private Integer id;
    private Integer profileId;
    private Integer commentId;
    private LocalDateTime createdDate;
    private LikeStatus status;
}
