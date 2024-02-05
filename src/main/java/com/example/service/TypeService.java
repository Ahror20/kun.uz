package com.example.service;

import com.example.dto.TypeDTO;
import com.example.entity.TypeEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TypeService {
    @Autowired
    private TypeRepository typeRepository;

    public TypeDTO create(TypeDTO dto) {
        Optional<TypeEntity> optional = typeRepository.findByOrderNumber(dto.getOrder_number());
        if (optional.isPresent()) {
            throw new AppBadException("There is an article type with such order number");
        }
        TypeEntity entity = new TypeEntity();
        entity.setOrderNumber(dto.getOrder_number());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());

        typeRepository.save(entity);

        dto.setId(entity.getId());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;

    }

    public boolean update(Integer id, TypeDTO dto) {
        Optional<TypeEntity> optionalById = typeRepository.findById(id);
        if (optionalById.isEmpty()) {
            throw new AppBadException("Article type with this ID was not found");
        }

        Optional<TypeEntity> optional = typeRepository.findByOrderNumber(dto.getOrder_number());
        if (optional.isPresent()) {
            throw new AppBadException("There is an article type with such order number");
        }

        dto.setUpdatedDate(LocalDateTime.now());

        int result = typeRepository.updateArticleType(dto.getOrder_number(), dto.getNameUz(), dto.getNameRu(), dto.getNameEn(), dto.getUpdatedDate(), id);
        if (result == 1) {
            return true;
        }
        throw new AppBadException("each fields is wrong");
    }

    public Boolean delete(Integer id) {
        Optional<TypeEntity> optionalById = typeRepository.findById(id);
        if (optionalById.isEmpty()) {
            throw new AppBadException("Article type with this ID was not found");
        }
        int result = typeRepository.delete(id);
        if (result == 1) {
            return true;
        }
        throw new AppBadException("each fields is wrong");
    }

    public PageImpl<TypeDTO> pagination(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable paging = PageRequest.of(page - 1, size, sort);

        Page<TypeEntity> studentPage = typeRepository.findAll(paging);

        List<TypeEntity> entityList = studentPage.getContent();
        long totalElements = studentPage.getTotalElements();

        List<TypeDTO> dtoList = new LinkedList<>();
        for (TypeEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }
        return new PageImpl<>(dtoList, paging, totalElements);

    }

    private TypeDTO toDTO(TypeEntity entity) {
        TypeDTO dto = new TypeDTO();
        dto.setId(entity.getId());
        dto.setOrder_number(entity.getOrderNumber());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setNameUz(entity.getNameUz());
        dto.setVisible(entity.getVisible());
        dto.setUpdatedDate(entity.getUpdatedDate());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public List<TypeDTO> getByLan(AppLanguage language) {
        List<TypeDTO> dtoList = new LinkedList<>();
        Iterable<TypeEntity> all = typeRepository.findAll();

        for (TypeEntity entity : all) {
            TypeDTO dto = new TypeDTO();
            dto.setId(entity.getId());
            switch (language) {
                case uz -> dto.setName(entity.getNameUz());
                case ru -> dto.setName(entity.getNameRu());
                default -> dto.setName(entity.getNameEn());
            }
            ;
            dtoList.add(dto);
        }
        return dtoList;
    }
}



