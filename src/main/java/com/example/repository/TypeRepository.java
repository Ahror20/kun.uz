package com.example.repository;

import com.example.entity.TypeEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TypeRepository extends CrudRepository<TypeEntity, Integer>, PagingAndSortingRepository<TypeEntity,Integer> {

    Optional<TypeEntity> findByOrderNumber(Integer orderNumber);

    List<TypeEntity> findAll();


    @Transactional
    @Modifying
    @Query("update TypeEntity set orderNumber = :orderNumber, nameUz = :name_uz, nameEn = :name_en , nameRu = :name_ru, updatedDate =:updatedDate where id = :id")
    int updateArticleType(@Param("orderNumber") Integer orderNumber,
                          @Param("name_uz") String name_uz,
                          @Param("name_ru") String name_ru,
                          @Param("name_en") String name_en,
                          @Param("updatedDate")LocalDateTime updatedDate,
                          @Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("update TypeEntity set visible = false where id =:id")
    int delete(@Param("id") Integer id);

}
