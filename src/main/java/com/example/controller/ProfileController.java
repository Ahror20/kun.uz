package com.example.controller;

import com.example.dto.*;
import com.example.enums.ProfileRole;
import com.example.service.AuthService;
import com.example.service.ProfileService;
import com.example.util.HttpRequestUtil;
import com.example.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;


    @PostMapping("/adm")
    public ResponseEntity<ProfileDTO> create(@RequestBody ProfileCRUDDTO dto,
                                             HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.create(dto));
    }
    @PutMapping("/adm/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
                                          @RequestBody ProfileCRUDDTO dto,
                                          HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.update(id, dto));
    }
    @PutMapping("/adm/any/{id}")
    public ResponseEntity<Boolean> updateDetail(@RequestBody ProfileCRUDDTO dto,
                                                HttpServletRequest request) {
        Integer id = HttpRequestUtil.getProfileId(request);
        return ResponseEntity.ok(profileService.updateOwn(id, dto));
    }
    @GetMapping("/adm/pagination")
    public ResponseEntity<PageImpl<ProfileDTO>> pagination(@RequestParam(value = "page" ,defaultValue = "1") Integer page,
                                                           @RequestParam(value = "size" , defaultValue = "1") Integer size,
                                                           HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.pagination(page, size));
    }
    @DeleteMapping("/adm/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id,
                                          HttpServletRequest request){
        HttpRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.deleteById(id));
    }

    @PostMapping("/adm/filter")
    public ResponseEntity<PageImpl<ProfileDTO>> filter(@RequestBody ProfileFilterDTO dto,
                                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                       HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        PageImpl<ProfileDTO> result = profileService.filter(dto, page, size);
        return ResponseEntity.ok(result);
    }
    @PutMapping("/adm/updateRole/{id}")
    public ResponseEntity<Boolean> updateRole(@PathVariable("id") Integer id,
                                              @RequestBody ProfileCRUDDTO dto,
                                              HttpServletRequest request){
        HttpRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.updateRole(id, dto.getRole()));
    }





}
