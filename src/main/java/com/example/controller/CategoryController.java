package com.example.controller;

import com.example.dto.CategoryDTO;
import com.example.dto.CreateCategoryDTO;
import com.example.dto.RegionDTO;
import com.example.enums.AppLanguage;
import com.example.enums.ProfileRole;
import com.example.service.CategoryService;
import com.example.util.HttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/adm")
    public ResponseEntity<CategoryDTO> create(@RequestBody CreateCategoryDTO dto,
                                              HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.create(dto));
    }
    @PutMapping("/adm/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
                                          @RequestBody CategoryDTO dto,
                                          HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.update(id, dto));
    }
    @DeleteMapping("/adm/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id,
                                          HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.delete(id));
    }
    @GetMapping("/adm")
    public ResponseEntity<List<CategoryDTO>> getAll(HttpServletRequest request){
        HttpRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        return ResponseEntity.of(categoryService.getAll());
    }
    @GetMapping("/geByLan")
    public ResponseEntity<List<CategoryDTO>> getByLan(@RequestParam(value = "lan",defaultValue = "uz") AppLanguage lan){
        return ResponseEntity.ok(categoryService.getByLan(lan));

    }

}
