package com.example.repository;

import com.example.entity.EmailSendHistoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmailSendHistoryRepository extends CrudRepository<EmailSendHistoryEntity,Integer>, PagingAndSortingRepository<EmailSendHistoryEntity,Integer> {

    List<EmailSendHistoryEntity> findByEmail(String email);

    List<EmailSendHistoryEntity> findByCreatedDateBetween(LocalDateTime fromDate, LocalDateTime toDate);

    Optional<EmailSendHistoryEntity> findTop1ByEmailOrderByCreatedDateDesc(String email);

    Long countByEmailAndCreatedDateBetween(String email, LocalDateTime from, LocalDateTime to);

    @Query("SELECT count (s) from EmailSendHistoryEntity s where s.email =?1 and s.createdDate between ?2 and ?3")
    Long countSendEmail(String email, LocalDateTime from, LocalDateTime to);



}
