package com.example.service;

import com.example.dto.SavedArticleCreateDTO;
import com.example.dto.SavedArticleDTO;
import com.example.entity.SavedArticleEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.ArticleRepository;
import com.example.repository.SavedArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SavedArticleService {
    @Autowired
    private ResourcesBundleService resourcesBundleService;
    @Autowired
    private SavedArticleRepository savedArticleRepository;
    @Autowired
    private ArticleRepository articleRepository;
    public SavedArticleDTO create(SavedArticleCreateDTO dto, Integer profileId) {
        SavedArticleEntity entity = new SavedArticleEntity();
        entity.setProfileId(profileId);
        entity.setArticleId(dto.getArticleId());
        savedArticleRepository.save(entity);
        return toDTO(entity);
    }

    private SavedArticleDTO toDTO(SavedArticleEntity entity){
        SavedArticleDTO dto =new SavedArticleDTO();
        dto.setId(entity.getId());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setProfileId(entity.getProfileId());
        dto.setArticleId(entity.getArticleId());
        dto.setTitle((articleRepository.findById(entity.getArticleId())).get().getTitle());
        dto.setDescription(articleRepository.findById(entity.getArticleId()).get().getDescription());
        dto.setImageId(articleRepository.findById(entity.getArticleId()).get().getPhotoID());
        dto.setImageUrl(articleRepository.findById(entity.getArticleId()).get().getPhoto().getPath());
        return dto;
    }

    public Boolean delete(String articleId, Integer profileId) {
        Optional<SavedArticleEntity> entity = savedArticleRepository.findByArticleIdAndProfileId(articleId,profileId);
        if (entity.isEmpty()){
            throw new AppBadException(resourcesBundleService.getMessage("saved-article.not.found", AppLanguage.en));
        }
        int effectRows = savedArticleRepository.delete(articleId,profileId);
        return effectRows == 1;

    }

    public List<SavedArticleDTO> getList(Integer profileId) {
        List<SavedArticleEntity> list = savedArticleRepository.findByProfileId(profileId);
        if (list.isEmpty()){
            throw new AppBadException(resourcesBundleService.getMessage("saved-article.not.found", AppLanguage.en));
        }

        List<SavedArticleDTO> dtoList = new ArrayList<>();
        for (SavedArticleEntity entity:list){
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }
}
