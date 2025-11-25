package com.yazilimxyz.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yazilimxyz.ticket.entity.Ticket;
import com.yazilimxyz.ticket.entity.TicketStatus;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
    // Belirli bir statüdeki ticketları getir (Örn: Açık olanları ver)
    List<Ticket> findByStatus(TicketStatus status);
    
    // Belirli bir kullanıcının açtığı ticketları getir
    List<Ticket> findByCreatedBy_Id(Long userId);

    // Email adresine göre o kişinin açtığı ticketları bul
    List<Ticket> findByCreatedBy_Email(String email);
}