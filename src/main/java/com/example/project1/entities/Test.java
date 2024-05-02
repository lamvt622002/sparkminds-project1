package com.example.project1.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

//@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "test")
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Test  extends AuditTable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;
}
