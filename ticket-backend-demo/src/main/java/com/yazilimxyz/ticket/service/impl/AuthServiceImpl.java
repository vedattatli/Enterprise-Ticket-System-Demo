package com.yazilimxyz.ticket.service.impl;

import com.yazilimxyz.ticket.dto.auth.LoginRequestDto;
import com.yazilimxyz.ticket.security.JwtTokenProvider;
import com.yazilimxyz.ticket.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String login(LoginRequestDto request) {
        // 1. Spring Security AuthenticationManager ile kullanıcıyı doğrula
        // Bu işlem gider DB'ye bakar, şifreleri karşılaştırır (Hash'li şifre ile)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // 2. Doğrulama başarılıysa context'e at
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Token üret ve döndür
        return jwtTokenProvider.generateToken(authentication);
    }
}