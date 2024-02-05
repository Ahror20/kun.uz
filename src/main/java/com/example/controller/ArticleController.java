package com.example.controller;

import com.example.dto.ArticleDTO;
import com.example.dto.CRUDArticleDTO;
import com.example.enums.ArticleStatus;
import com.example.enums.ProfileRole;
import com.example.service.ArticleService;
import com.example.util.HttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @PostMapping("/moderator")
    public ResponseEntity<ArticleDTO> create(@RequestBody CRUDArticleDTO dto,
                                             HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.create(dto,profileId));
    }
    @DeleteMapping("/moderator/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String id,
                                          HttpServletRequest request){
        HttpRequestUtil.getProfileId(request, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.delete(id));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ArticleDTO> update(@PathVariable("id") String id){
        return ResponseEntity.ok(articleService.update(id));
    }

}
