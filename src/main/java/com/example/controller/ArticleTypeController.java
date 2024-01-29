package com.example.controller;

import com.example.dto.ArticleTypeDTO;
import com.example.service.ArticleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/article_type")
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService articleTypeService;

    @PostMapping("")
    public ResponseEntity<ArticleTypeDTO> create(@RequestBody ArticleTypeDTO dto) {
        return ResponseEntity.ok(articleTypeService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
                                          @RequestBody ArticleTypeDTO dto) {
        return ResponseEntity.ok(articleTypeService.update(id, dto));
    }

    @GetMapping("delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(articleTypeService.delete(id));
    }

    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<ArticleTypeDTO>> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                               @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(articleTypeService.pagination(page, size));
    }
//    @GetMapping("/geByLan")
//    public ResponseEntity<Map<Integer,String>> getByLan(@RequestParam String lan){
//        return ResponseEntity.ok(articleTypeService.getByLan(lan));
//
//    }
//
//    @GetMapping("test/{id}")
//    public ResponseEntity<Optional<ArticleTypeDTO>> getByLan(@PathVariable("id") Integer id,
//                                                             @RequestParam String lan){
//        return ResponseEntity.ok(articleTypeService.getByLan(id,lan));
//
//    }

}
