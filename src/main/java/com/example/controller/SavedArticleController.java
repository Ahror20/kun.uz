package com.example.controller;

import com.example.dto.SavedArticleCreateDTO;
import com.example.dto.SavedArticleDTO;
import com.example.service.SavedArticleService;
import com.example.util.HttpRequestUtil;
import com.example.util.SpringSecurityUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/saved-article")
public class SavedArticleController {
    @Autowired
    private SavedArticleService savedArticleService;

    @PostMapping
    @PreAuthorize(value = "hasAnyRole('MODERATOR','USER','ADMIN')")
    public ResponseEntity<SavedArticleDTO> create(@Valid @RequestBody SavedArticleCreateDTO dto) {
        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(savedArticleService.create(dto, profileId));
    }

    @DeleteMapping("/{articleId}")
    @PreAuthorize(value = "hasAnyRole('MODERATOR','USER','ADMIN')")
    public ResponseEntity<Boolean> create(@PathVariable("articleId") String articleId) {
        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(savedArticleService.delete(articleId, profileId));
    }

    @GetMapping
    @PreAuthorize(value = "hasAnyRole('MODERATOR','USER','ADMIN')")
    public ResponseEntity<List<SavedArticleDTO>> getList(){
        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(savedArticleService.getList(profileId));
    }
}
