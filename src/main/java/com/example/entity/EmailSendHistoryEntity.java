package com.example.entity;

import com.example.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "email_send_history")
public class EmailSendHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,columnDefinition = "text")
    private String message;
    @ManyToOne
    @JoinColumn(name = "profile_id",nullable = false)
    private ProfileEntity profile;
    @Column(nullable = false)
    private String email;
    @Column(name = "created_date",nullable = false)
    private LocalDateTime createdDate;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;
    
}
