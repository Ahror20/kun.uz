package com.example.service;

import com.example.dto.*;
import com.example.entity.CommentEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.exp.AppBadException;
import com.example.repository.CommentCustomRepository;
import com.example.repository.CommentRepository;
import com.example.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private CommentCustomRepository commentCustomRepository;
    public CommentDTO create(Integer profileId, CommentCreateDTO dto) {
        CommentEntity entity =new CommentEntity();
        entity.setContent(dto.getContent());
        entity.setArticleId(dto.getArticleId());
        entity.setProfileId(profileId);
        commentRepository.save(entity);
        return toDTO(entity);
    }
    private CommentDTO toDTO(CommentEntity entity){
        CommentDTO dto =new CommentDTO();
        dto.setId(entity.getId());
        dto.setProfileId(entity.getProfileId());
        dto.setArticleId(entity.getArticleId());
        dto.setContent(entity.getContent());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());
        return dto;
    }

    public Boolean update(Integer profileId, CommentCreateDTO dto,Integer id) {
        Optional<CommentEntity> entity = commentRepository.findByProfileIdAndArticleId(profileId, dto.getArticleId());
        if (entity.isEmpty()){
            log.warn("content not found");
            throw new AppBadException("content not found");
        }
        if (!profileId.equals(entity.get().getProfileId())){
            log.warn("content not found");
            throw new AppBadException("you are not allowed");
        }
        if (entity.get().getVisible().equals(false)){
            throw new AppBadException("this commit has been deleted");
        }
        int effectRows = commentRepository.updateCommit(dto.getContent(), dto.getArticleId(), LocalDateTime.now(),id);
        return effectRows == 1;

    }

    public Boolean delete(Integer id, Integer profileId) {
        Optional<CommentEntity> entity =commentRepository.findByIdAndProfileId(id,profileId);
        if (entity.isEmpty()){
            log.warn("commit not found");
            throw new AppBadException("commit not found");
        }
        Optional<ProfileEntity> profile = profileRepository.findById(profileId);
        if (!entity.get().getProfileId().equals(profileId) || !profile.get().getRole().equals(ProfileRole.ADMIN)){
            log.warn("content not found");
            throw new AppBadException("you are not allowed");
        }

        int effectRows = commentRepository.delete(id);
        return effectRows==1;
    }

    public List<ListCommitDTO> getCommitListByArticleId(String articleId) {
        List<CommentEntity> list = commentRepository.findByArticleId(articleId);
        if (list.isEmpty()){
            throw new AppBadException("comments not found");
        }

        List<ListCommitDTO> dtoList = new ArrayList<>();
        for (CommentEntity entity: list){
            ListCommitDTO dto = new ListCommitDTO();
            dto.setId(entity.getId());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setUpdateDate(entity.getUpdatedDate());
            dto.setProfileName(entity.getProfile().getName());
            dto.setProfileSurname(entity.getProfile().getSurname());
            dto.setProfileId(entity.getProfileId());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public PageImpl<CommentDTO> pagination(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"createdDate");
        Pageable paging =  PageRequest.of(page-1,size,sort);

        Page<CommentEntity> studentPage=commentRepository.findAll(paging);
        List<CommentEntity> entityList = studentPage.getContent();
        long totalElements = studentPage.getTotalElements();

        List<CommentDTO> dtoList = new LinkedList<>();
        for (CommentEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }
        return new PageImpl<>(dtoList,paging,totalElements);
    }

    public PageImpl<CommentDTO> filter(CommentFilterDTO dto, Integer page, Integer size) {
        PaginationResultDTO<CommentEntity> paginationResult = commentCustomRepository.filter(dto, page, size);

        List<CommentDTO> dtoList = new LinkedList<>();
        for (CommentEntity entity : paginationResult.getList()) {
            dtoList.add(toDTO(entity));
        }

        Pageable paging = PageRequest.of(page - 1, size);
        return new PageImpl<>(dtoList, paging, paginationResult.getTotalSize());
    }
}
