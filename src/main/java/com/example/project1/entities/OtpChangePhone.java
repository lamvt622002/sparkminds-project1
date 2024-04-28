package com.example.project1.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "otp_change_phone")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OtpChangePhone extends AuditTable {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NonNull
    private String newPhoneNumber;

    @NonNull
    private String otp;

    @NonNull
    private Integer isUsed;

    @NonNull
    private LocalDateTime expireTime;

}
