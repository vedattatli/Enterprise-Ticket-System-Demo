package com.yazilimxyz.ticket.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ROLE_ADMIN, ROLE_USER gibi değerler tutacağız
    @Column(nullable = false, unique = true)
    private String name;
}