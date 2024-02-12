package com.example.controller;

import com.example.dto.JwtDTO;
import com.example.dto.RegionDTO;
import com.example.enums.AppLanguage;
import com.example.enums.ProfileRole;
import com.example.exp.AppBadException;
import com.example.service.RegionService;
import com.example.util.HttpRequestUtil;
import com.example.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping("/adm")
    public ResponseEntity<RegionDTO> create(@RequestBody RegionDTO dto,
                                            HttpServletRequest request) {
      //  HttpRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.create(dto));
    }
    @PutMapping("/adm/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
                                          @RequestBody RegionDTO dto,
                                          HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.update(id, dto));
    }
    @DeleteMapping("/adm/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id,
                                          HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.delete(id));
    }
    @GetMapping("/adm")
    public ResponseEntity<List<RegionDTO>> getAll(HttpServletRequest request){
        HttpRequestUtil.getProfileId(request,ProfileRole.ADMIN);
        return ResponseEntity.of(regionService.getAll());
    }
    @GetMapping("/geByLan")
    public ResponseEntity<List<RegionDTO>> getByLan(@RequestParam(value = "lan",defaultValue = "uz") AppLanguage lan){
        return ResponseEntity.ok(regionService.getByLan(lan));
    }

}
