package com.example.controller;

import com.example.dto.AuthDTO;
import com.example.dto.ProfileDTO;
import com.example.dto.RegistrationDTO;
import com.example.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Tag(name = "Authorization Api list", description = "Api list for Authorization")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation( summary = "Api for login", description = "this api used for authorization")
    public ResponseEntity<ProfileDTO> login(@Valid @RequestBody AuthDTO auth){
        log.info("Login {} ", auth.getEmail());
        return ResponseEntity.ok(authService.auth(auth));
    }
    @PostMapping("/registration")
    @Operation( summary = "Api for registration", description = "this api used for registration")
    public ResponseEntity<Boolean> registration(@RequestBody RegistrationDTO dto) {
        log.info("registration {} ", dto.getEmail());
        return ResponseEntity.ok(authService.registration(dto));
    }
    @GetMapping("/verification/email/{jwt}")
    @Operation( summary = "Api for emailVerification", description = "this api used for emailVerification")
    public ResponseEntity<String> emailVerification(@PathVariable("jwt") String jwt) {
        log.info("emailVerification {} ",jwt);
        return ResponseEntity.ok(authService.emailVerification(jwt));
    }
    @GetMapping("/verification/code/{code}")
    @Operation( summary = "Api for smsVerification", description = "this api used for smsVerification")
    public ResponseEntity<String> smsVerification(@PathVariable("code") String code) {
        return ResponseEntity.ok(authService.smsVerification(code));
    }
    @GetMapping("/resentEmail")
    @Operation( summary = "Api for resentEmail", description = "this api used for resentEmail")
    public ResponseEntity<String> resentEmail(@RequestParam("email") String email){
        return ResponseEntity.ok(authService.resentEmail(email));
    }
    @GetMapping("/resentPhone/{phone}")
    @Operation( summary = "Api for resentPhone", description = "this api used for resentPhone")
    public ResponseEntity<String> resentPhone(@PathVariable String phone){
        return ResponseEntity.ok(authService.resentPhone(phone));
    }


}
