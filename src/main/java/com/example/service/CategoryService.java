package com.example.service;

import com.example.dto.CategoryDTO;
import com.example.dto.CreateCategoryDTO;
import com.example.dto.RegionDTO;
import com.example.entity.CategoryEntity;
import com.example.entity.RegionEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    public CategoryDTO create(CreateCategoryDTO dto) {
        Optional<CategoryEntity> optional = categoryRepository.findByOrderNumber(dto.getOrder_number());
        if (optional.isPresent()){
            throw new AppBadException("There is an category with such order number");
        }
        var entity = new CategoryEntity();
        entity.setOrderNumber(dto.getOrder_number());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());

        categoryRepository.save(entity);

        return toDTO(entity);
    }
    private CategoryDTO toDTO(CategoryEntity entity) {
        CategoryDTO dto = new CategoryDTO();
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

    public Boolean update(Integer id, CategoryDTO dto) {
        Optional<CategoryEntity> optionalById = categoryRepository.findById(id);
        if (optionalById.isEmpty()){
            throw new AppBadException("category with this ID was not found");
        }

        Optional<CategoryEntity> optional = categoryRepository.findByOrderNumber(dto.getOrder_number());
        if (optional.isPresent()){
            throw new AppBadException("There is an region with such order number");
        }

        dto.setUpdatedDate(LocalDateTime.now());

        int result = categoryRepository.updateCategory(dto.getOrder_number(), dto.getNameUz(),dto.getNameRu(),dto.getNameEn(),dto.getUpdatedDate(),id);
        if (result == 1){
            return true;
        }
        throw new AppBadException("each fields is wrong");

    }

    public Boolean delete(Integer id) {
        Optional<CategoryEntity> optionalById = categoryRepository.findById(id);
        if (optionalById.isEmpty()){
            throw new AppBadException("region with this ID was not found");
        }
        int result  = categoryRepository.delete(id);
        if (result == 1){
            return true;
        }
        throw new AppBadException("each fields is wrong");
    }

    public Optional<List<CategoryDTO>> getAll() {
        List<CategoryEntity> regionList = categoryRepository.findAll();
        List<CategoryDTO> regions = new ArrayList<>();

        for (CategoryEntity entity:regionList){
            regions.add(toDTO(entity));
        }
        return Optional.of(regions);
    }

    public List<CategoryDTO> getByLan(AppLanguage lan) {
        List<CategoryDTO> dtoList=new LinkedList<>();
        Iterable<CategoryEntity> all = categoryRepository.findAll();

        for (CategoryEntity entity : all) {
            CategoryDTO dto = new CategoryDTO();
            dto.setId(entity.getId());
            switch (lan) {
                case uz -> dto.setName(entity.getNameUz());
                case ru -> dto.setName(entity.getNameRu());
                default -> dto.setName( entity.getNameEn());
            };
            dtoList.add(dto);
        }
        return dtoList;
    }
}
