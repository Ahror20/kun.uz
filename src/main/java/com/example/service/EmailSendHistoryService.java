package com.example.service;

import com.example.dto.EmailSendHistoryDTO;
import com.example.dto.ProfileDTO;
import com.example.entity.EmailSendHistoryEntity;
import com.example.entity.ProfileEntity;
import com.example.exp.AppBadException;
import com.example.repository.EmailSendHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class EmailSendHistoryService {
    @Autowired
    private EmailSendHistoryRepository emailSendHistoryRepository;
    public void saveEmailHistory(EmailSendHistoryDTO emailSendHistoryDTO) {
        EmailSendHistoryEntity entity = new EmailSendHistoryEntity();
        entity.setMessage(emailSendHistoryDTO.getMessage());
        entity.setEmail(emailSendHistoryDTO.getEmail());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setProfile(emailSendHistoryDTO.getProfile());
        entity.setStatus(emailSendHistoryDTO.getStatus());
        emailSendHistoryRepository.save(entity);
    }

    public List<EmailSendHistoryDTO> getByEmail(String email) {
        List<EmailSendHistoryEntity> list = emailSendHistoryRepository.findByEmail(email);
        if (list.isEmpty()){
            throw new AppBadException("profile not found");
        }
        List<EmailSendHistoryDTO> dtoList = new LinkedList<>();

        for (EmailSendHistoryEntity entity:list){
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }
    private EmailSendHistoryDTO toDTO(EmailSendHistoryEntity entity){
        EmailSendHistoryDTO dto = new EmailSendHistoryDTO();
        dto.setId(entity.getId());
        dto.setMessage(entity.getMessage());
        dto.setEmail(entity.getEmail());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public List<EmailSendHistoryDTO> getByDate(LocalDate date) {
        LocalDateTime fromDate = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime toDate = LocalDateTime.of(date, LocalTime.MAX);

        List<EmailSendHistoryEntity> list = emailSendHistoryRepository.findByCreatedDateBetween(fromDate,toDate);
        if (list.isEmpty()){
            throw new AppBadException("email history is empty");
        }

        List<EmailSendHistoryDTO> dtoList = new LinkedList<>();
        for (EmailSendHistoryEntity entity:list){
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    public PageImpl<EmailSendHistoryDTO> pagination(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"createdDate");
        Pageable paging =  PageRequest.of(page-1,size,sort);

        Page<EmailSendHistoryEntity> studentPage=emailSendHistoryRepository.findAll(paging);
        List<EmailSendHistoryEntity> entityList = studentPage.getContent();
        long totalElements = studentPage.getTotalElements();

        List<EmailSendHistoryDTO> dtoList = new LinkedList<>();
        for (EmailSendHistoryEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }
        return new PageImpl<>(dtoList,paging,totalElements);

    }
}
