package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "category")
public class CategoryEntity extends BaseEntity {
    @Column(name = "order_number",unique = true)
    private Integer orderNumber;
    @Column(nullable = false, name = "name_uz")
    private String nameUz;
    @Column(nullable = false,name = "name_ru")
    private String nameRu;
    @Column(nullable = false, name = "name_en")
    private String nameEn;
}
