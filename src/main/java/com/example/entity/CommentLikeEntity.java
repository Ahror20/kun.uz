package com.example.entity;

import com.example.enums.LikeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "comment_like")
public class CommentLikeEntity {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;

    @Column(name = "comment_id")
    private Integer commentId;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private CommentEntity comment;
    @Column(name = "comment_id")
    private LocalDateTime createdDate;
    @Column(name = "status")
    private LikeStatus status;
}
