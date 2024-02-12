package com.example.service;

import com.example.entity.ArticleTypeEntity;
import com.example.repository.ArticleRepository;
import com.example.repository.ArticleTypeRepository;
import com.example.repository.ProfileRepository;
import com.example.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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

    //    private ArticleTypeDTO toDTO(ArticleTypeEntity entity){
//        ArticleTypeDTO dto = new ArticleTypeDTO();
//        dto.setId(entity.getId());
//        dto.setType(entity.getType());
//        dto.setArticle(entity.getArticle());
//        dto.setCreatedDate(entity.getCreatedDate());
//        return dto;
//    }
    public void create(String articleId, List<Integer> typesIdList) {
        for (Integer typeId : typesIdList) {
            create(articleId, typeId);
        }
    }

    public void create(String articleId, Integer typesId) {
        ArticleTypeEntity entity = new ArticleTypeEntity();
        entity.setArticleId(articleId);
        entity.setTypeId(typesId);
        articleTypeRepository.save(entity);
    }









    public void merge(String articleId, List<Integer> newList) {
        //oldin [1,2,3,]
        //keyin [1,2,3,5,6,7,8]
        List<ArticleTypeEntity> articleList = articleTypeRepository.findArticleId(articleId);
        for (ArticleTypeEntity newArticleTypeEntity : articleList) {
            int count = 0;
            for (Integer natId : newList) {
                if (newArticleTypeEntity.getTypeId().equals(natId)) {
                    count++;
                }
            }
            if (count == 0) {
                articleTypeRepository.deleteEn(newArticleTypeEntity.getArticleId(), newArticleTypeEntity.getTypeId());
            }
            else {
                articleTypeRepository.updateDate(LocalDateTime.now(),newArticleTypeEntity.getArticleId(), newArticleTypeEntity.getTypeId());
            }
        }

        for (Integer atpId : newList) {         //keyin [1,2,3,5,6,7,8]
            Optional<ArticleTypeEntity> optional = articleTypeRepository.getArticleTypeId(articleId, atpId);
            if (optional.isEmpty()) {
                create(articleId, atpId);
            }
        }
    }


}
