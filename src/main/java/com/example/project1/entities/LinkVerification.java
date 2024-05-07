package com.example.project1.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "link_verification")
public class LinkVerification extends AuditTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @NonNull
    private String email;

    @NonNull
    private String token;

    @NonNull
    private Integer isUsed;

    @NonNull
    private LocalDateTime expireTime;

}
