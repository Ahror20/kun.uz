package com.example.controller;

import com.example.dto.*;
import com.example.enums.ProfileRole;
import com.example.exp.AppBadException;
import com.example.service.CommentService;
import com.example.util.HttpRequestUtil;
import com.example.util.SpringSecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<CommentDTO> create(@Valid @RequestBody CommentCreateDTO dto) {
        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(commentService.create(profileId,dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@Valid @PathVariable("id") Integer id,
                                                 @RequestBody CommentCreateDTO dto) {
        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(commentService.update(profileId,dto,id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id){
        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        return ResponseEntity.ok(commentService.delete(id,profileId));
    }
    @GetMapping("/getCommitListByArticleId/{articleId}")
    @PreAuthorize(value = "hasAnyRole('ADMIN','MODERATOR','PUBLISHER','USER')")
    public ResponseEntity<List<ListCommitDTO>> getCommitListByArticleId(@PathVariable("articleId") String articleId){
        return ResponseEntity.ok(commentService.getCommitListByArticleId(articleId));
    }
    @GetMapping("/pagination")
    @PreAuthorize(value = "hasAnyRole('ADMIN','MODERATOR','PUBLISHER','USER')")
    public ResponseEntity<PageImpl<CommentDTO>> pagination(@RequestParam(value = "page" ,defaultValue = "1") Integer page,
                                                           @RequestParam(value = "size" , defaultValue = "1") Integer size) {
        return ResponseEntity.ok(commentService.pagination(page, size));
    }
    @PostMapping("/filter")
    @PreAuthorize(value = "hasAnyRole('ADMIN','MODERATOR','PUBLISHER','USER')")

    public ResponseEntity<PageImpl<CommentDTO>> filter(@RequestBody CommentFilterDTO dto,
                                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "10") Integer size) {

        PageImpl<CommentDTO> result = commentService.filter(dto, page, size);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/getRepliedList/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<List<GetRepliedListDTO>> getRepliedList(@PathVariable("id") Integer id){
        return ResponseEntity.ok(commentService.getRepliedList(id));
    }

}


