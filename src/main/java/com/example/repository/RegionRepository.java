package com.example.repository;

import com.example.entity.RegionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RegionRepository extends CrudRepository<RegionEntity,Integer>, PagingAndSortingRepository<RegionEntity,Integer> {

    Optional<RegionEntity> findByOrderNumber(Integer orderNumber);

    @Transactional
    @Modifying
    @Query("update RegionEntity set orderNumber = :orderNumber, nameUz = :name_uz, nameEn = :name_en , nameRu = :name_ru, updatedDate =:updatedDate where id = :id")
    int updateArticleType(@Param("orderNumber") Integer orderNumber,
                          @Param("name_uz") String name_uz,
                          @Param("name_ru") String name_ru,
                          @Param("name_en") String name_en,
                          @Param("updatedDate")LocalDateTime updatedDate,
                          @Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("update RegionEntity set visible = false where id =:id")
    int delete(@Param("id") Integer id);

    List<RegionEntity> findAll();
}
