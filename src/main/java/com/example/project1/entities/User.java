package com.example.project1.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Table(name = "users")
public class User extends AuditTable {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String phoneNumber;

    @NonNull
    private String email;

    @NonNull
    private String password;

    @NonNull
    private Integer failedLoginAttempts;

    private LocalDateTime lockoutTime;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "role")
    @ToString.Exclude
    private Authorities role;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<LinkVerification> linkVerifications;

    @NonNull
    private Integer status;
}
