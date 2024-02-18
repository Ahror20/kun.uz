package com.example.controller;

import com.example.dto.TypeDTO;
import com.example.enums.AppLanguage;
import com.example.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/type")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @PostMapping("/adm")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<TypeDTO> create(@RequestBody TypeDTO dto) {
        return ResponseEntity.ok(typeService.create(dto));
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
                                          @RequestBody TypeDTO dto) {
        return ResponseEntity.ok(typeService.update(id, dto));
    }

    @GetMapping("/adm/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(typeService.delete(id));
    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<PageImpl<TypeDTO>> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                        @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(typeService.pagination(page, size));
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
public ResponseEntity<List<TypeDTO>> getByLan(@RequestParam(value = "lan",defaultValue = "uz") AppLanguage lan){
    return ResponseEntity.ok(typeService.getByLan(lan));

}

}
