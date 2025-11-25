package com.yazilimxyz.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yazilimxyz.ticket.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Select * from users where email = '...'
    Optional<User> findByEmail(String email);
    
    // Email daha önce alınmış mı kontrolü için
    boolean existsByEmail(String email);
}