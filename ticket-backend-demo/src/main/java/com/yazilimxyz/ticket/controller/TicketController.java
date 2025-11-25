package com.yazilimxyz.ticket.controller;

import com.yazilimxyz.ticket.dto.ticket.TicketRequestDto;
import com.yazilimxyz.ticket.dto.ticket.TicketResponseDto;
import com.yazilimxyz.ticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.yazilimxyz.ticket.entity.TicketStatus;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    // 1. Yeni Ticket Oluştur (ID istemez)
    @PostMapping
    public ResponseEntity<TicketResponseDto> createTicket(
            @RequestBody TicketRequestDto request,
            Authentication authentication
    ) {
        String username = authentication.getName();
        TicketResponseDto createdTicket = ticketService.createTicket(request, username);
        return ResponseEntity.ok(createdTicket);
    }

    // 2. Tüm Ticketları Getir (Sadece ADMIN)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TicketResponseDto>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    // 3. ID ile Ticket Getir (User sadece kendi ticketını görebilir)
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDto> getTicketById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String username = authentication.getName();
        return ResponseEntity.ok(ticketService.getTicketById(id, username));
    }
    // 1. TICKET DURUMUNU DEĞİŞTİR (Admin Only)
    // Örnek: PUT /api/tickets/1/status?status=CLOSED
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}/status")
    public ResponseEntity<TicketResponseDto> updateStatus(@PathVariable Long id, @RequestParam TicketStatus status) {
        return ResponseEntity.ok(ticketService.updateTicketStatus(id, status));
    }

    // 2. TICKET'I BİRİNE ATA (Admin Only)
    // Örnek: PUT /api/tickets/1/assign/2 (1 nolu ticket'ı 2 nolu user'a ata)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}/assign/{userId}")
    public ResponseEntity<TicketResponseDto> assignTicket(@PathVariable Long id, @PathVariable Long userId) {
        return ResponseEntity.ok(ticketService.assignTicketToUser(id, userId));
    }
}
