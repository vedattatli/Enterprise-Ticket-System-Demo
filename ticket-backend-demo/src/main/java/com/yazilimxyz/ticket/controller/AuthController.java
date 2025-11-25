package com.yazilimxyz.ticket.controller;

import com.yazilimxyz.ticket.dto.auth.AuthResponseDto;
import com.yazilimxyz.ticket.dto.auth.LoginRequestDto;
import com.yazilimxyz.ticket.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto loginDto) {
        String token = authService.login(loginDto);
        return ResponseEntity.ok(new AuthResponseDto(token, "Giriş Başarılı!"));
    }
}