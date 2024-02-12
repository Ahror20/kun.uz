package com.example.controller;

import com.example.dto.*;
import com.example.enums.ProfileRole;
import com.example.service.CommentService;
import com.example.util.HttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<CommentDTO> create(@Valid @RequestBody CommentCreateDTO dto,
                                             HttpServletRequest request) {
        Integer profileId = HttpRequestUtil.getProfileId(request);
        return ResponseEntity.ok(commentService.create(profileId,dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@Valid @PathVariable("id") Integer id,
                                                 @RequestBody CommentCreateDTO dto,
                                                 HttpServletRequest request) {
        Integer profileId = HttpRequestUtil.getProfileId(request);
        return ResponseEntity.ok(commentService.update(profileId,dto,id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id,
                                          HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request);
        return ResponseEntity.ok(commentService.delete(id,profileId));
    }
    @GetMapping("/getCommitListByArticleId/{articleId}")
    public ResponseEntity<List<ListCommitDTO>> getCommitListByArticleId(@PathVariable("articleId") String articleId,
                                                                        HttpServletRequest request){
        HttpRequestUtil.getProfileId(request);
        return ResponseEntity.ok(commentService.getCommitListByArticleId(articleId));
    }
    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<CommentDTO>> pagination(@RequestParam(value = "page" ,defaultValue = "1") Integer page,
                                                           @RequestParam(value = "size" , defaultValue = "1") Integer size,
                                                           HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(commentService.pagination(page, size));
    }
    @PostMapping("/filter")
    public ResponseEntity<PageImpl<CommentDTO>> filter(@RequestBody CommentFilterDTO dto,
                                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                       HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        PageImpl<CommentDTO> result = commentService.filter(dto, page, size);
        return ResponseEntity.ok(result);
    }
}


