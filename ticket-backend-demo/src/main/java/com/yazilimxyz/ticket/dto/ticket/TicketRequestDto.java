package com.yazilimxyz.ticket.dto.ticket;

import com.yazilimxyz.ticket.entity.TicketPriority;

import lombok.Data;

@Data
public class TicketRequestDto {
    private String title;
    private String description;
    private TicketPriority priority; // LOW, HIGH vs.
}