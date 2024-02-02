package com.example.service;

import com.example.dto.EmailSendHistoryDTO;
import com.example.dto.SmsHistoryDTO;
import com.example.entity.EmailSendHistoryEntity;
import com.example.entity.SmsHistoryEntity;
import com.example.exp.AppBadException;
import com.example.repository.SmsHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class SmsHistoryService {
    @Autowired
    private SmsHistoryRepository smsHistoryRepository;
    public List<SmsHistoryDTO> getByPhone(String phone) {
        List<SmsHistoryEntity> list = smsHistoryRepository.findByPhone(phone);
        if (list.isEmpty()){
            throw new AppBadException("sms list is empty");
        }
        List<SmsHistoryDTO> dtoList = new LinkedList<>();

        for (SmsHistoryEntity entity:list){
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    public SmsHistoryDTO toDTO(SmsHistoryEntity entity) {
        SmsHistoryDTO dto = new SmsHistoryDTO();
        dto.setId(entity.getId());
        dto.setMessage(entity.getMessage());
        dto.setType(entity.getType());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPhone(entity.getPhone());
        dto.setStatus(entity.getStatus());
        dto.setUsedDate(entity.getUsedDate());
        return dto;
    }


    public List<SmsHistoryDTO> getByDate(LocalDate date) {
        LocalDateTime fromDate = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime toDate = LocalDateTime.of(date, LocalTime.MAX);

        List<SmsHistoryEntity> list = smsHistoryRepository.findByCreatedDateBetween(fromDate,toDate);
        if (list.isEmpty()){
            throw new AppBadException("sms history is empty");
        }

        List<SmsHistoryDTO> dtoList = new LinkedList<>();
        for (SmsHistoryEntity entity:list){
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    public PageImpl<SmsHistoryDTO> pagination(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"createdDate");
        Pageable paging =  PageRequest.of(page-1,size,sort);

        Page<SmsHistoryEntity> studentPage=smsHistoryRepository.findAll(paging);
        List<SmsHistoryEntity> entityList = studentPage.getContent();
        long totalElements = studentPage.getTotalElements();

        List<SmsHistoryDTO> dtoList = new LinkedList<>();
        for (SmsHistoryEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }
        return new PageImpl<>(dtoList,paging,totalElements);
    }
}
