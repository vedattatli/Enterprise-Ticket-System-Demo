package com.yazilimxyz.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yazilimxyz.ticket.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    // Select * from roles where name = '...'
    Optional<Role> findByName(String name);
}