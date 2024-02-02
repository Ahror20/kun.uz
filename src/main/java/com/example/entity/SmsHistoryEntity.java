package com.example.entity;

import com.example.enums.SmsStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "sms_history")
public class SmsHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String message;
    private String type;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SmsStatus status;
    @Column(nullable = false,name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "used_date")
    private LocalDateTime usedDate;

}
