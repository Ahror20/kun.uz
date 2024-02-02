package com.example.controller;

import com.example.dto.AuthDTO;
import com.example.dto.ProfileDTO;
import com.example.dto.RegistrationDTO;
import com.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Receiver;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> login(@RequestBody AuthDTO auth){
        return ResponseEntity.ok(authService.auth(auth));
    }
    @PostMapping("/registration")
    public ResponseEntity<Boolean> registration(@RequestBody RegistrationDTO dto) {
        return ResponseEntity.ok(authService.registration(dto));
    }
    @GetMapping("/verification/email/{jwt}")
    public ResponseEntity<String> emailVerification(@PathVariable("jwt") String jwt) {
        return ResponseEntity.ok(authService.emailVerification(jwt));
    }
    @GetMapping("/verification/code/{code}")
    public ResponseEntity<String> smsVerification(@PathVariable("code") String code) {
        return ResponseEntity.ok(authService.smsVerification(code));
    }
    @GetMapping("/resentEmail")
    public ResponseEntity<String> resentEmail(@RequestParam("email") String email){
        return ResponseEntity.ok(authService.resentEmail(email));
    }
    @GetMapping("/resentPhone/{phone}")
    public ResponseEntity<String> resentPhone(@PathVariable String phone){
        return ResponseEntity.ok(authService.resentPhone(phone));
    }


}
