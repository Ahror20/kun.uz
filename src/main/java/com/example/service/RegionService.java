package com.example.service;

import com.example.dto.RegionDTO;
import com.example.entity.RegionEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public RegionDTO create(RegionDTO dto) {
        Optional<RegionEntity> optional = regionRepository.findByOrderNumber(dto.getOrder_number());
        if (optional.isPresent()){
            throw new AppBadException("There is an region with such order number");
        }
        var entity = new RegionEntity();
        entity.setOrderNumber(dto.getOrder_number());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());

        regionRepository.save(entity);

        dto.setId(entity.getId());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }


    public Boolean update(Integer id, RegionDTO dto) {
        Optional<RegionEntity> optionalById = regionRepository.findById(id);
        if (optionalById.isEmpty()){
            throw new AppBadException("region with this ID was not found");
        }

        Optional<RegionEntity> optional = regionRepository.findByOrderNumber(dto.getOrder_number());
        if (optional.isPresent()){
            throw new AppBadException("There is an region with such order number");
        }

        dto.setUpdatedDate(LocalDateTime.now());

        int result = regionRepository.updateArticleType(dto.getOrder_number(), dto.getNameUz(),dto.getNameRu(),dto.getNameEn(),dto.getUpdatedDate(),id);
        if (result == 1){
            return true;
        }
        throw new AppBadException("each fields is wrong");
    }

    public Boolean delete(Integer id) {
        Optional<RegionEntity> optionalById = regionRepository.findById(id);
        if (optionalById.isEmpty()){
            throw new AppBadException("region with this ID was not found");
        }
        int result  = regionRepository.delete(id);
        if (result == 1){
            return true;
        }
        throw new AppBadException("each fields is wrong");
    }

    public Optional<List<RegionDTO>> getAll() {
        List<RegionEntity> regionList = regionRepository.findAll();
        List<RegionDTO> regions = new ArrayList<>();

        for (RegionEntity entity:regionList){
            regions.add(toDTO(entity));
        }
        return Optional.of(regions);
    }

    private RegionDTO toDTO(RegionEntity entity) {
        RegionDTO dto = new RegionDTO();
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

    public List<RegionDTO> getByLan(AppLanguage language) {
        List<RegionDTO> dtoList=new LinkedList<>();
        Iterable<RegionEntity> all = regionRepository.findAll();

        for (RegionEntity entity : all) {
            RegionDTO dto = new RegionDTO();
            dto.setId(entity.getId());
            switch (language) {
                case uz -> dto.setName(entity.getNameUz());
                case ru -> dto.setName(entity.getNameRu());
                default -> dto.setName( entity.getNameEn());
            };
            dtoList.add(dto);
        }
        return dtoList;
    }
}
