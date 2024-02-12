package com.example.controller;

import com.example.dto.ArticleLikeDTO;
import com.example.dto.CommentLikeCreateDTO;
import com.example.dto.CommentLikeDTO;
import com.example.dto.CreateArticleLikeDTO;
import com.example.service.ArticleLikeService;
import com.example.service.CommentLikeService;
import com.example.util.HttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment-like")
public class CommentLikeController {
    @Autowired
    private CommentLikeService commentLikeService;

    @PostMapping("/like")
    public ResponseEntity<CommentLikeDTO> like(@RequestBody CommentLikeCreateDTO dto,
                                               HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request);
        return ResponseEntity.ok(commentLikeService.like(dto,profileId));
    }
    @PostMapping("/dislike")
    public ResponseEntity<CommentLikeDTO> dislike(@RequestBody CommentLikeCreateDTO dto,
                                                  HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request);
        return ResponseEntity.ok(commentLikeService.dislike(dto,profileId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> remove(@PathVariable("id") Integer commentId ,
                                          HttpServletRequest request){
        Integer profileId = HttpRequestUtil.getProfileId(request);
        return ResponseEntity.ok(commentLikeService.remove(commentId,profileId));

    }


}
