package com.example.controller;

import com.example.dto.ArticleTypeDTO;
import com.example.dto.CreateArticleTypeDTO;
import com.example.enums.ProfileRole;
import com.example.service.ArticleTypeService;
import com.example.util.HttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("article-type")
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService articleTypeService;

    @PostMapping("/publisher")
    public ResponseEntity<ArticleTypeDTO> create(@RequestBody CreateArticleTypeDTO dto,
                                                 HttpServletRequest request){
       Integer id  =  HttpRequestUtil.getProfileId(request, ProfileRole.PUBLISHER);
        return ResponseEntity.ok(articleTypeService.create(dto,id));

    }
}
