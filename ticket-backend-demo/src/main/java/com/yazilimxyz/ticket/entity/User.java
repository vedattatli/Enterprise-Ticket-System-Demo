package com.yazilimxyz.ticket.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users") // "user" PostgreSQL'de özel kelime, o yüzden "users" yaptık
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String fullName;

    // Kullanıcı aktif mi pasif mi? (Silmek yerine pasife çekeceğiz)
    @Builder.Default
    private boolean active = true;

    // İLİŞKİ: Bir kullanıcının birden fazla rolü olabilir (User + Admin gibi)
    // FetchType.EAGER: User'ı çektiğimde rollerini de hemen getir.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}