package com.yazilimxyz.ticket.dto.ticket;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

import com.yazilimxyz.ticket.entity.TicketPriority;
import com.yazilimxyz.ticket.entity.TicketStatus;

@Data
@Builder
public class TicketResponseDto {
    private Long id;
    private String title;
    private String description;
    private TicketStatus status;
    private TicketPriority priority;
    private LocalDateTime createdAt;
    private String createdBy; // User objesi yerine sadece ismini dönelim
}