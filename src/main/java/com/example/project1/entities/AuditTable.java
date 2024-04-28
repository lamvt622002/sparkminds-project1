package com.example.project1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class AuditTable {
    @CreatedBy
    private String createdBy;

    @CreatedDate
    @Column(name = "created_at", columnDefinition = "DATETIME")
    private LocalDateTime createdAt;

    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "updated_at", columnDefinition = "DATETIME")
    private LocalDateTime updatedAt;

   // mapper package
}
