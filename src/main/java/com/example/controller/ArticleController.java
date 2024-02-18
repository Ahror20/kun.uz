package com.example.controller;

import com.example.dto.*;
import com.example.enums.ProfileRole;
import com.example.service.ArticleService;
import com.example.util.HttpRequestUtil;
import com.example.util.SpringSecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("/moderator")
    @PreAuthorize(value = "hasRole('MODERATOR')")
    public ResponseEntity<ArticleDTO> create(@Valid @RequestBody CreateArticleDTO dto) {
        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(articleService.create(dto, profileId));
    }

    @DeleteMapping("/moderator/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String id,
                                          HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_MODERATOR);
        return ResponseEntity.ok(articleService.delete(id));
    }

    @PutMapping("/moderator/{id}")
    public ResponseEntity<Boolean> update(@Valid @PathVariable("id") String id,
                                             @RequestBody UpdateArticleDTO dto,
                                             HttpServletRequest request) {
        Integer profileId = HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_MODERATOR);
        return ResponseEntity.ok(articleService.update(id, dto, profileId));
    }
    @PutMapping("/changeStatusById/{id}") //publisher
    public ResponseEntity<Boolean> changeStatusById(@PathVariable("id") String id,
                                                    @RequestBody UpdateArticleStatusDTO dto,
                                                    HttpServletRequest request){
        Integer publisherId = HttpRequestUtil.getProfileId(request,ProfileRole.ROLE_PUBLISHER);
        return ResponseEntity.ok(articleService.changeStatusByID(id,publisherId,dto));
    }
    @GetMapping("/getLast5ArticleByTypes/{id}") // any
    public ResponseEntity<List<ArticleShortInfoDTO>> getLast5ArticleByTypes(@PathVariable("id") Integer id,
                                                                            @RequestParam("limit") Integer limit){
        return ResponseEntity.ok(articleService.getLast5ArticleByTypes(id,limit));
    }
    @PostMapping("/getLast8ArticlesIdNotInclude")
    public ResponseEntity<List<ArticleShortInfoDTO>> getLastGetLast8Articles(@RequestBody String [] ids){
        return ResponseEntity.ok(articleService.getLastGetLast8Articles(ids));
    }

    @PostMapping("/getLast4ArticleByTypes/{typeId}")
    @PreAuthorize(value = "hasRole('MODERATOR')")
    public ResponseEntity<List<ArticleShortInfoDTO>> getLast4ArticleByTypes(@PathVariable("typeId") Integer typeId,
                                                                            @RequestBody String [] id){
        return ResponseEntity.ok(articleService.getLast4ArticleByTypes(typeId,id));
    }
    @GetMapping("/get4MostReadArticles")
    public ResponseEntity<List<ArticleShortInfoDTO>> get4MostReadArticles(){
        return ResponseEntity.ok(articleService.get4MostReadArticles());
    }


}
