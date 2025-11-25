package com.yazilimxyz.ticket.util;

import com.yazilimxyz.ticket.entity.*;
import com.yazilimxyz.ticket.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder; // EKLENDİ
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final PasswordEncoder passwordEncoder; // EKLENDİ

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            System.out.println("--- VERİTABANI BOŞ, TEST VERİLERİ YÜKLENİYOR ---");

            Role adminRole = roleRepository.save(Role.builder().name("ROLE_ADMIN").build());
            Role userRole = roleRepository.save(Role.builder().name("ROLE_USER").build());

            User admin = User.builder()
                    .email("admin@ticket.com")
                    .password(passwordEncoder.encode("admin123")) // ŞİFRELENDİ
                    .fullName("Vedat Admin")
                    .roles(Collections.singleton(adminRole))
                    .active(true)
                    .build();
            userRepository.save(admin);

            User normalUser = User.builder()
                    .email("user@ticket.com")
                    .password(passwordEncoder.encode("user123")) // ŞİFRELENDİ
                    .fullName("Ahmet User")
                    .roles(Collections.singleton(userRole))
                    .active(true)
                    .build();
            userRepository.save(normalUser);

            // Ticket kısmı aynı kalabilir...
            Ticket ticket = Ticket.builder()
                    .title("Bilgisayarım Açılmıyor")
                    .description("Power tuşuna basıyorum ama tık yok.")
                    .priority(TicketPriority.HIGH)
                    .status(TicketStatus.OPEN)
                    .createdBy(normalUser)
                    .build();
            ticketRepository.save(ticket);

            System.out.println("--- TEST VERİLERİ YÜKLENDİ: Admin, User ve 1 Ticket ---");
        }
    }
}