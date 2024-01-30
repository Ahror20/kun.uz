package com.example.controller;

import com.example.dto.ArticleTypeDTO;
import com.example.dto.RegionDTO;
import com.example.enums.AppLanguage;
import com.example.service.ArticleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/article_type")
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService articleTypeService;

    @PostMapping("/adm")
    public ResponseEntity<ArticleTypeDTO> create(@RequestBody ArticleTypeDTO dto) {
        return ResponseEntity.ok(articleTypeService.create(dto));
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
                                          @RequestBody ArticleTypeDTO dto) {
        return ResponseEntity.ok(articleTypeService.update(id, dto));
    }

    @GetMapping("/adm/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(articleTypeService.delete(id));
    }

    @GetMapping("/adm/pagination")
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
@GetMapping("/geByLan")
public ResponseEntity<List<ArticleTypeDTO>> getByLan(@RequestParam(value = "lan",defaultValue = "uz") AppLanguage lan){
    return ResponseEntity.ok(articleTypeService.getByLan(lan));

}

}
