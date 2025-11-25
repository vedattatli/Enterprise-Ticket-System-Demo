package com.yazilimxyz.ticket.service;

import com.yazilimxyz.ticket.dto.ticket.TicketRequestDto;
import com.yazilimxyz.ticket.dto.ticket.TicketResponseDto;
import com.yazilimxyz.ticket.entity.TicketStatus;

import java.util.List;

public interface TicketService {

    // Yeni ticket oluştur (ID’siz, owner = username)
    TicketResponseDto createTicket(TicketRequestDto ticketRequestDto, String username);

    // Tüm ticketlar (admin için kullanıyoruz)
    List<TicketResponseDto> getAllTickets();

    // ID ile ticket getir (kim istediğini de bilerek)
    TicketResponseDto getTicketById(Long id, String username);

    TicketResponseDto updateTicketStatus(Long id, TicketStatus status);
    TicketResponseDto assignTicketToUser(Long ticketId, Long userId);
}
