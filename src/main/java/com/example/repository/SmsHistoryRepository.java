package com.example.repository;

import com.example.entity.SmsHistoryEntity;
import com.example.enums.ProfileStatus;
import com.example.enums.SmsStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public interface SmsHistoryRepository  extends CrudRepository<SmsHistoryEntity,Integer>, PagingAndSortingRepository<SmsHistoryEntity,Integer> {
    @Query("SELECT count (s) from SmsHistoryEntity s where s.phone =?1 and s.createdDate between ?2 and ?3")
    Long countSendPhone(String email, LocalDateTime from, LocalDateTime to);



    Optional<SmsHistoryEntity> findByMessage(String code);

    List<SmsHistoryEntity> findByPhone(String phone);

    @Transactional
    @Modifying
    @Query("Update SmsHistoryEntity  set status =?2, usedDate=?3 where id = ?1")
    void   updateStatus(Integer id, SmsStatus status, LocalDateTime time);

    List<SmsHistoryEntity> findByCreatedDateBetween(LocalDateTime fromDate, LocalDateTime toDate);

}
