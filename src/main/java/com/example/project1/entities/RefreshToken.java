package com.example.project1.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "refreshToken")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class RefreshToken extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    @NonNull
    private User user;

    @NonNull
    private String token;

    @NonNull
    private LocalDateTime expireTime;
}
