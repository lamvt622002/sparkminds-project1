package com.example.project1.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "staff")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@AllArgsConstructor
public class Staff extends User{
    @NonNull
    private String fullName;

    @NonNull
    private LocalDate hireDate;
}
