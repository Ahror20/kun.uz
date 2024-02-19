package com.example.service;

import com.example.dto.*;
import com.example.entity.ArticleEntity;
import com.example.entity.ArticleTypeEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.AppLanguage;
import com.example.enums.ArticleStatus;
import com.example.exp.AppBadException;
import com.example.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
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
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleTypeService articleTypeService;
    @Autowired
    private ArticleTypeRepository articleTypeRepository;
    @Autowired
    private ResourcesBundleService resourcesBundleService;
    @Autowired
    private ArticleCustomRepository articleCustomRepository;

    public ArticleDTO create(CreateArticleDTO dto, Integer profileId) {
        ArticleEntity entity = new ArticleEntity();
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);
        entity.setPhotoID(dto.getPhotoId());
        entity.setViewCount(0);
        entity.setSharedCount(0);
        entity.setLikeCount(0);
        entity.setDislikeCount(0);
        entity.setModeratorId(profileId);
        articleRepository.save(entity);

        articleTypeService.create(entity.getId(), dto.getArticleType());
        return toDTO(entity);

    }

    public Boolean delete(String id) {
        Optional<ArticleEntity> optional = articleRepository.findById(id);
        if (optional.isEmpty()) {
            log.warn("article not found");
            throw new AppBadException("article not found");
        }
        int effectRows = articleRepository.delete(id);
        return effectRows == 1;

    }

    public Boolean update(String id, UpdateArticleDTO dto, Integer profileId) {
        Optional<ArticleEntity> optional = articleRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("article not found");
        }

        int effectRows = articleRepository.update(dto.getTitle(), dto.getDescription(), dto.getContent(), dto.getShared_count(), dto.getPhotoId(), dto.getRegionId(), dto.getCategoryId(), profileId, id);
        if (effectRows != 1) {
            return false;
        }
        articleTypeService.merge(id, dto.getArticleType());
        return true;
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

    public Boolean changeStatusByID(String id, Integer publisherId, UpdateArticleStatusDTO dto) {
        Optional<ArticleEntity> optional = articleRepository.findById(id);
        if (optional.isEmpty()) {
            log.warn("article not found");
            throw new AppBadException("article not found");
        }
        articleRepository.changeStatusById(dto.getStatus(), publisherId, LocalDateTime.now(), id);
        return true;
    }

    public List<ArticleShortInfoDTO> getLast5ArticleByTypes(Integer id, Integer limit) {
        List<ArticleTypeEntity> listTypes = articleTypeRepository.findByTypeId(id);
        if (listTypes.isEmpty()) {
            log.warn("did not find such type of article");
            throw new AppBadException("did not find such type of article!");
        }

        List<ArticleTypeEntity> list = articleTypeRepository.findTop5ByTypes(id, limit);
        List<ArticleShortInfoDTO> articleList = new ArrayList<>();

        for (ArticleTypeEntity entity : list) {
            articleList.add(shortInfoDTO(entity.getArticle()));
        }
        return articleList;

    }

    public ArticleShortInfoDTO shortInfoDTO(ArticleEntity entity) {
        ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
        dto.setId(entity.getId());
        dto.setImageId(entity.getPhoto().getId());
        dto.setImageUrl(entity.getPhoto().getPath());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setPublishedDate(entity.getPublishedDate());
        return dto;
    }

    public List<ArticleShortInfoDTO> getLastGetLast8Articles(String[] ids) {
        List<ArticleEntity> getAll = (List<ArticleEntity>) articleRepository.findAll();
        if (getAll.isEmpty()) {
            throw new AppBadException(resourcesBundleService.getMessage("article.not.found", AppLanguage.en));
        }


        List<ArticleEntity> list = articleRepository.get8Article(ids);
        List<ArticleShortInfoDTO> dtoList = new ArrayList<>();
        for (ArticleEntity entity : list) {
            dtoList.add(shortInfoDTO(entity));
        }
        return dtoList;
    }

    public List<ArticleShortInfoDTO> getLast4ArticleByTypes(Integer typeId, String[] id) {
        List<ArticleEntity> list = articleRepository.getLastArticle4ArticleByTypes(typeId, id);
        List<ArticleShortInfoDTO> dtoList = new ArrayList<>();
        for (ArticleEntity entity : list) {
            dtoList.add(shortInfoDTO(entity));
        }
        return dtoList;
    }

    public List<ArticleShortInfoDTO> get4MostReadArticles() {
        List<ArticleEntity> articleEntities = articleRepository.get4MostReadArticles();
        return getArticleShortInfoDTOS(articleEntities);
    }

    public List<ArticleShortInfoDTO> getLast5ArticleByTypesAndByRegionKey(Integer typeId, Integer regionId) {
        List<ArticleEntity> articleEntities = articleRepository.getLast5ArticleByTypesAndByRegionKey(typeId, regionId);
        return getArticleShortInfoDTOS(articleEntities);

    }

    @NotNull
    private List<ArticleShortInfoDTO> getArticleShortInfoDTOS(List<ArticleEntity> articleEntities) {
        if (articleEntities.isEmpty()) {
            throw new AppBadException(resourcesBundleService.getMessage("article.not.found", AppLanguage.en));
        }
        List<ArticleShortInfoDTO> dtoList = new ArrayList<>();

        for (ArticleEntity entity : articleEntities) {
            dtoList.add(shortInfoDTO(entity));
        }
        return dtoList;
    }

    public PageImpl<ArticleShortInfoDTO> getArticleListByRegionKeyPagination(Integer reginId, Integer page, Integer size) {

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable paging = PageRequest.of(page - 1, size, sort);

        Page<ArticleEntity> studentPage = articleRepository.findByRegionId(reginId, paging);
        List<ArticleEntity> entityList = studentPage.getContent();
        long totalElements = studentPage.getTotalElements();

        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        for (ArticleEntity entity : entityList) {
            dtoList.add(shortInfoDTO(entity));
        }

        return new PageImpl<>(dtoList, paging, totalElements);

    }

    public Boolean increaseArticleViewCountByArticleId(String id) {
        Optional<ArticleEntity> optional = articleRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException(resourcesBundleService.getMessage("article.not.found", AppLanguage.en));
        }
        int effectRows = articleRepository.increaseArticleViewCountByArticleId(id);
        return effectRows == 1;
    }

    public Boolean increaseShareViewCountByArticleId(String id) {
        Optional<ArticleEntity> optional = articleRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException(resourcesBundleService.getMessage("article.not.found", AppLanguage.en));
        }
        int effectRows = articleRepository.increaseShareViewCountByArticleId(id);
        return effectRows == 1;
    }

    public PageImpl<ArticleShortInfoDTO> filter(ArticleFilterDTO dto, Integer page, Integer size) {
        PaginationResultDTO<ArticleEntity> paginationResult = articleCustomRepository.filter(dto, page, size);

        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        for (ArticleEntity entity : paginationResult.getList()) {
            dtoList.add(shortInfoDTO(entity));
        }

        Pageable paging = PageRequest.of(page - 1, size);
        return new PageImpl<>(dtoList, paging, paginationResult.getTotalSize());

    }
}
