package com.example.service;

import com.example.dto.ArticleTypeDTO;
import com.example.entity.ArticleTypeEntity;
import com.example.exp.AppBadException;
import com.example.repository.ArticleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;
    public ArticleTypeDTO create(ArticleTypeDTO dto) {
        Optional<ArticleTypeEntity> optional = articleTypeRepository.findByOrderNumber(dto.getOrder_number());
        if (optional.isPresent()){
            throw new AppBadException("There is an article type with such order number");
        }
        ArticleTypeEntity entity = new ArticleTypeEntity();
        entity.setOrderNumber(dto.getOrder_number());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());

        articleTypeRepository.save(entity);

        dto.setId(entity.getId());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;

    }
    public boolean update(Integer id ,ArticleTypeDTO dto){
        Optional<ArticleTypeEntity> optionalById = articleTypeRepository.findById(id);
        if (optionalById.isEmpty()){
            throw new AppBadException("Article type with this ID was not found");
        }

        Optional<ArticleTypeEntity> optional = articleTypeRepository.findByOrderNumber(dto.getOrder_number());
        if (optional.isPresent()){
            throw new AppBadException("There is an article type with such order number");
        }

        dto.setUpdatedDate(LocalDateTime.now());

        int result = articleTypeRepository.updateArticleType(dto.getOrder_number(), dto.getNameUz(),dto.getNameRu(),dto.getNameEn(),dto.getUpdatedDate(),id);
        if (result == 1){
           return true;
        }
        throw new AppBadException("each fields is wrong");
    }

    public Boolean delete(Integer id) {
        Optional<ArticleTypeEntity> optionalById = articleTypeRepository.findById(id);
        if (optionalById.isEmpty()){
            throw new AppBadException("Article type with this ID was not found");
        }
        int result  = articleTypeRepository.delete(id);
        if (result == 1){
            return true;
        }
        throw new AppBadException("each fields is wrong");
    }
    public PageImpl<ArticleTypeDTO> pagination(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable paging = PageRequest.of(page - 1, size, sort);

        Page<ArticleTypeEntity> studentPage = articleTypeRepository.findAll(paging);

        List<ArticleTypeEntity> entityList = studentPage.getContent();
        long totalElements = studentPage.getTotalElements();

        List<ArticleTypeDTO> dtoList = new LinkedList<>();
        for (ArticleTypeEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }
        return new PageImpl<>(dtoList, paging, totalElements);

    }

    private ArticleTypeDTO toDTO(ArticleTypeEntity entity) {
            ArticleTypeDTO dto = new ArticleTypeDTO();
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

    public Map<Integer, String> getByLan(Integer id,String lan) {
        if (!(lan.equalsIgnoreCase("uz") || lan.equalsIgnoreCase("ru") || lan.equalsIgnoreCase("eng"))){
            throw new AppBadException("The language format was incorrectly selected");
        }
        List<ArticleTypeEntity> articleTypeList = articleTypeRepository.findAll();
        Map<Integer,String> nameList = new HashMap<>();
        for (ArticleTypeEntity entity: articleTypeList){
            if (lan.equalsIgnoreCase("uz")){
                nameList.put(entity.getId(), entity.getNameUz());
            }
        }
        for (ArticleTypeEntity entity: articleTypeList){
            if (lan.equalsIgnoreCase("ru")){
                nameList.put(entity.getId(), entity.getNameRu());
            }
        }
        for (ArticleTypeEntity entity: articleTypeList){
            if (lan.equalsIgnoreCase("eng")){
                nameList.put(entity.getId(), entity.getNameEn());
            }
        }
        return nameList;
    }


}
