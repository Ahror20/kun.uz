package com.example.controller;

import com.example.dto.ArticleLikeDTO;
import com.example.dto.CreateArticleLikeDTO;
import com.example.service.ArticleLikeService;
import com.example.util.HttpRequestUtil;
import com.example.util.SpringSecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article_like")
public class ArticleLikeController {
    @Autowired
    private ArticleLikeService articleLikeService;

    @PostMapping("/like")
    @PreAuthorize(value = "hasAnyRole('MODERATOR')")
    public ResponseEntity<ArticleLikeDTO> like(@RequestBody CreateArticleLikeDTO dto){
        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(articleLikeService.like(dto,profileId));
    }
    @PostMapping("/dislike")
    @PreAuthorize(value = "hasAnyRole('MODERATOR')")
    public ResponseEntity<ArticleLikeDTO> dislike(@RequestBody CreateArticleLikeDTO dto){
        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(articleLikeService.dislike(dto,profileId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> remove(@PathVariable("id") String articleId,
                                          HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request);
        return ResponseEntity.ok(articleLikeService.remove(articleId,profileId));

    }
}
