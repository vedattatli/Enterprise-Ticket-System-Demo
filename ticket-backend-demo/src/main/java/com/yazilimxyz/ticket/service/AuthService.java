package com.yazilimxyz.ticket.service;

import com.yazilimxyz.ticket.dto.auth.LoginRequestDto;

public interface AuthService {
    String login(LoginRequestDto loginRequestDto);
}