package com.example.controller;

import com.example.dto.JwtDTO;
import com.example.dto.RegionDTO;
import com.example.enums.AppLanguage;
import com.example.enums.ProfileRole;
import com.example.exp.AppBadException;
import com.example.service.RegionService;
import com.example.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping("")
    public ResponseEntity<RegionDTO> create(@RequestBody RegionDTO dto,
                                            @RequestHeader("Authorization") String jwt) {
        JwtDTO jwtDTO = JWTUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)){
            throw new AppBadException("sizga ruxsat yo`q");
        }
        return ResponseEntity.ok(regionService.create(dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
                                          @RequestBody RegionDTO dto) {
        return ResponseEntity.ok(regionService.update(id, dto));
    }
    @GetMapping("delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(regionService.delete(id));
    }
    @GetMapping("")
    public ResponseEntity<List<RegionDTO>> getAll(){
        return ResponseEntity.of(regionService.getAll());
    }
    @GetMapping("/geByLan")
    public ResponseEntity<List<RegionDTO>> getByLan(@RequestParam(value = "lan",defaultValue = "uz") AppLanguage lan){
        return ResponseEntity.ok(regionService.getByLan(lan));

    }

}
