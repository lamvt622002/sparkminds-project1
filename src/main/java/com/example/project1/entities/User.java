package com.example.project1.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Table(name = "users")
public class User extends BaseEntity{
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private LocalDate birthDay;

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

    @NonNull
    private Integer isDelete;

}
