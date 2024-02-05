package com.example.service;

import com.example.dto.ArticleTypeDTO;
import com.example.dto.CreateArticleTypeDTO;
import com.example.entity.ArticleEntity;
import com.example.entity.ArticleTypeEntity;
import com.example.entity.ProfileEntity;
import com.example.entity.TypeEntity;
import com.example.exp.AppBadException;
import com.example.repository.ArticleRepository;
import com.example.repository.ArticleTypeRepository;
import com.example.repository.ProfileRepository;
import com.example.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private ProfileRepository profileRepository;
    public ArticleTypeDTO create(CreateArticleTypeDTO dto, Integer id) {
        Optional<ProfileEntity> profile = profileRepository.findById(id);
        if (profile.isEmpty()){
            throw new AppBadException("profile not found");
        }
        Optional<ArticleEntity> article = articleRepository.findById(dto.getArticleId());
        if (article.isEmpty()){
            throw new AppBadException("article not found");
        }
        if (article.get().getVisible().equals(false)){
            throw new AppBadException("this article has been deleted");
        }
        Optional<TypeEntity> type = typeRepository.findById(dto.getTypeId());
        if (type.isEmpty()){
            throw new AppBadException("type not found");
        }
        ArticleTypeEntity entity = new ArticleTypeEntity();
        entity.setPublisher(profile.get());
        entity.setType(type.get());
        entity.setArticle(article.get());
        articleTypeRepository.save(entity);

        return toDTO(entity);
    }

    private ArticleTypeDTO toDTO(ArticleTypeEntity entity){
        ArticleTypeDTO dto = new ArticleTypeDTO();
        dto.setPublisher(entity.getPublisher());
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setArticle(entity.getArticle());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
