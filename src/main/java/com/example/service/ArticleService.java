package com.example.service;

import com.example.dto.ArticleDTO;
import com.example.dto.CRUDArticleDTO;
import com.example.entity.ArticleEntity;
import com.example.entity.CategoryEntity;
import com.example.entity.ProfileEntity;
import com.example.entity.RegionEntity;
import com.example.enums.ArticleStatus;
import com.example.enums.ProfileRole;
import com.example.exp.AppBadException;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ArticleDTO create(CRUDArticleDTO dto, Integer profileId) {
        Optional<ProfileEntity> profileEntity = profileRepository.findById(profileId);
        if (profileEntity.isEmpty()) {
            throw new AppBadException("profile not found");
        } else if (!profileEntity.get().getRole().equals(ProfileRole.MODERATOR)) {
            throw new AppBadException("you are not moderator");
        }
        if (dto.getTitle().length() < 3 || dto.getDescription().length() < 3) {
            throw new AppBadException("title or description is wrong");
        }
        Optional<RegionEntity> regionEntity = regionRepository.findById(dto.getRegionId());
        if (regionEntity.isEmpty()) {
            throw new AppBadException("region not found");
        }
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(dto.getCategoryId());
        if (categoryEntity.isEmpty()) {
            throw new AppBadException("category not found");
        }
        ArticleEntity entity = new ArticleEntity();
        entity.setCategory(categoryEntity.get());
        entity.setRegion(regionEntity.get());
        entity.setContent(dto.getContent());
        entity.setDescription(dto.getDescription());
        entity.setTitle(dto.getTitle());
        entity.setModerator(profileEntity.get());
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);
        entity.setSharedCount(0);
        entity.setViewCount(0);
        articleRepository.save(entity);
        return toDTO(entity);
    }

    private ArticleDTO toDTO(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setDescription(entity.getDescription());
        dto.setCategory(entity.getCategory());
        dto.setModerator(entity.getModerator());
        dto.setRegion(entity.getRegion());
        dto.setStatus(entity.getStatus());
        dto.setSharedCount(entity.getSharedCount());
        dto.setVisible(entity.getVisible());
        dto.setPublishedDate(entity.getPublishedDate());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setViewCount(entity.getViewCount());
        return dto;
    }

    public Boolean delete(String id) {
        Optional<ArticleEntity> optional = articleRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("article not found");
        }
        int effectRows = articleRepository.delete(id);
        return effectRows == 1;

    }

    public ArticleDTO update(String id) {
        return null;
    }
}
