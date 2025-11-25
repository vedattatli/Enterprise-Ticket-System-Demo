package com.yazilimxyz.ticket.controller;

import com.yazilimxyz.ticket.entity.User;
import com.yazilimxyz.ticket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;

    // Sadece ADMIN rolü olanlar buraya girebilir!
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") 
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        // Gerçek projede User yerine UserResponseDto dönmek lazım ama şimdilik hızlı olsun diye User dönüyoruz.
        return ResponseEntity.ok(userRepository.findAll());
    }

    // Dashboard Özeti
    @GetMapping("/stats")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> getDashboardStats() {
        // İleride buraya özel bir DTO yapılabilir ama şimdilik basit string dönelim
        long totalUsers = userRepository.count();
        // Repository'ye count metodu eklemek gerekebilir veya findAll().size() (performanssız ama şimdilik ok)
        return ResponseEntity.ok("Toplam Kullanıcı: " + totalUsers + ", Sistem Çalışıyor!");
    }
}