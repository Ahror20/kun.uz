package com.example.repository;

import com.example.entity.EmailSendHistoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EmailSendHistoryRepository extends CrudRepository<EmailSendHistoryEntity,Integer>, PagingAndSortingRepository<EmailSendHistoryEntity,Integer> {

    List<EmailSendHistoryEntity> findByEmail(String email);

    List<EmailSendHistoryEntity> findByCreatedDateBetween(LocalDateTime fromDate, LocalDateTime toDate);

}
