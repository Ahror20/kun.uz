package com.example.service;

import com.example.dto.ArticleDTO;
import com.example.dto.ArticleTypeDTO;
import com.example.dto.CRUDArticleDTO;
import com.example.entity.*;
import com.example.exp.AppBadException;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ArticleTypeRepository articleTypeRepository;
    public ArticleDTO create(CRUDArticleDTO dto, Integer profileId) {
//        if (dto.getTitle().length()<3 || dto.getDescription().length()<3){
//            throw new AppBadException("title or description is wrong");
//        }
//        Optional<RegionEntity> regionEntity = regionRepository.findById(dto.getRegionId());
//        if (regionEntity.isEmpty()){
//            throw new AppBadException("region not found");
//        }
//        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(dto.getCategoryId());
//        if (categoryEntity.isEmpty()){
//            throw new AppBadException("category not found");
//        }
       return null;


    }
}
