package com.yazilimxyz.ticket.service.impl;

import com.yazilimxyz.ticket.dto.ticket.TicketRequestDto;
import com.yazilimxyz.ticket.dto.ticket.TicketResponseDto;
import com.yazilimxyz.ticket.entity.Ticket;
import com.yazilimxyz.ticket.entity.TicketStatus;
import com.yazilimxyz.ticket.entity.User;
import com.yazilimxyz.ticket.repository.TicketRepository;
import com.yazilimxyz.ticket.repository.UserRepository;
import com.yazilimxyz.ticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    // ✔ ID’siz Ticket oluşturma
    @Override
    public TicketResponseDto createTicket(TicketRequestDto request, String username) {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + username));

        Ticket ticket = Ticket.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .priority(request.getPriority())
                .status(TicketStatus.OPEN)
                .createdBy(user)
                .build();

        Ticket saved = ticketRepository.save(ticket);
        return mapToDto(saved);
    }

    // ✔ ADMIN → tüm ticketlar
    // ✔ USER → sadece kendi ticket’ları
    @Override
    public List<TicketResponseDto> getAllTickets() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        List<Ticket> tickets;

        if (isAdmin) {
            tickets = ticketRepository.findAll();
        } else {
            tickets = ticketRepository.findByCreatedBy_Email(email);
        }

        return tickets.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // ✔ USER başkasının ticket detayını göremez (güvenlik kontrolü eklendi)
    @Override
    public TicketResponseDto getTicketById(Long id, String username) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket bulunamadı: " + id));

        boolean isOwner = ticket.getCreatedBy().getEmail().equals(username);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !isOwner) {
            throw new RuntimeException("Bu ticket sana ait değil!");
        }

        return mapToDto(ticket);
    }

    // ✔ Entity → DTO dönüştürme
    private TicketResponseDto mapToDto(Ticket ticket) {
        return TicketResponseDto.builder()
                .id(ticket.getId())
                .title(ticket.getTitle())
                .description(ticket.getDescription())
                .priority(ticket.getPriority())
                .status(ticket.getStatus())
                .createdAt(ticket.getCreatedAt())
                .createdBy(ticket.getCreatedBy().getFullName())
                .build();
    }
    // 1. DURUM GÜNCELLEME (Sadece Admin/Agent yapabilmeli)
    @Override
    public TicketResponseDto updateTicketStatus(Long id, TicketStatus status) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket bulunamadı"));

        ticket.setStatus(status);
        ticketRepository.save(ticket);
        return mapToDto(ticket);
    }

    // 2. TICKET ATAMA (Admin, ticket'ı bir personele atar)
    @Override
    public TicketResponseDto assignTicketToUser(Long ticketId, Long userId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket bulunamadı"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Atanacak kullanıcı bulunamadı"));

        ticket.setAssignedTo(user);
        ticketRepository.save(ticket);
        return mapToDto(ticket);
    }
}
