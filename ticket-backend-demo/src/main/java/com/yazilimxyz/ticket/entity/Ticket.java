package com.yazilimxyz.ticket.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    // Açıklama uzun olabilir, length artırıyoruz
    @Column(length = 1000)
    private String description;

    // ENUM değerleri veritabanında String olarak (OPEN, CLOSED gibi) tutulsun
    // Ordinal (0,1,2) tutulursa sıralama değişince veri bozulur, String garantidir.
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Enumerated(EnumType.STRING)
    private TicketPriority priority;

    // Otomatik oluşturulma tarihi
    @CreationTimestamp
    private LocalDateTime createdAt;

    // Otomatik güncellenme tarihi (her save işleminde değişir)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // İLİŞKİ: Ticket'ı kim açtı?
    @ManyToOne
    @JoinColumn(name = "created_by_id") // DB'deki kolon adı
    private User createdBy;

    // İLİŞKİ: Ticket kime atandı? (Agent)
    @ManyToOne
    @JoinColumn(name = "assigned_to_id")
    private User assignedTo;
}